package me.ngrid.jiggly

import java.lang.reflect.Constructor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.{BoxShapeBuilder, CapsuleShapeBuilder, ConeShapeBuilder, SphereShapeBuilder}
import com.badlogic.gdx.graphics.g3d.{Material, Model, ModelInstance}
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.math.{MathUtils, Matrix4, Vector3}
import com.badlogic.gdx.physics.bullet.collision._
import com.badlogic.gdx.utils.Disposable

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{Await, Future}

/**
  * in the spirit of references, this is the tutorial i am following for this class.
  *
  * https://xoppa.github.io/blog/using-the-libgdx-3d-physics-bullet-wrapper-part1/
  *
  */
object BoxAndBall {

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  def main(args: Array[String]): Unit = {
    BulletInit

    val f = Future.traverse(0 to 10) { _ =>
      val st = bulletState
      val f = Future(simulate(st))
      f.onComplete(_ => st.dispose())
      f
    }

    println(Await.result(f, 20.seconds).toSet)
  }

  def simulate(st: BulletState): Int = {
    var res = 0

    while (true) {
      step(st, 1f/30f)
      val c = st.instances.count(_.moving)
      if(c > 0) println(c)
    }

    res
  }

  def step(st: BulletState, dt: Float): Unit = {
    import st._
    instances.map { obj =>
      if (obj.moving) {
        obj.instance.transform.trn(0f, -dt, 0f)
        obj.body.setWorldTransform(obj.instance.transform)
        if (checkCollision(dispatcher, obj.body, instances(0).body))
          // FIXME: the bug is in here, we copy the object, but dont replace it inside of the state.
          // it actually creates a new arraybuffer, but this isnt how i want this to go down.
          obj.copy(moving = false)
        else
          obj
      }
    }

    spawnTimer -= dt

    if (spawnTimer < 0f) {
      //spawn a random object every 1.5 seconds
      st.spawnRandomObject()
      st.spawnTimer = 1.5f
    }
  }

  def bulletState: BulletState = {
    val model = createModel

    val cons = mutable.Map(
      // this is a pretty shitty way of doing this, specifying the collision shapes dimensions in completely different place than
      // the actual objects shape
      "ground" -> GameObject("ground", model, new btBoxShape(new Vector3(2.5f, 0.5f, 2.5f))),
      "sphere" -> GameObject("sphere", model, new btSphereShape(0.5f)),
      "box" -> GameObject("box", model, new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f))),
      "cone" -> GameObject("cone", model, new btConeShape(0.5f, 2f)),
      "capsule" -> GameObject("capsule", model, new btCapsuleShape(0.5f, 1f))
    )

    val instances = ArrayBuffer(cons("ground")())


    val (collisionConfig, dispatcher) = {
      val cc = new btDefaultCollisionConfiguration()
      val d = new btCollisionDispatcher(cc)

      cc -> d
    }

    BulletState(model, cons, instances, collisionConfig, dispatcher, 0)
  }


  def createModel: Model = {

    val mb = new ModelBuilder
    mb.begin()

    mb.node().id = "ground"
    BoxShapeBuilder.build(
      mb.part("box", GL20.GL_TRIANGLES, Usage.Normal | Usage.Position, new Material(ColorAttribute.createDiffuse(Color.RED))),
      5f, 1f, 5f)

    mb.node().id = "ball"
    SphereShapeBuilder.build(
      mb.part("sphere", GL20.GL_TRIANGLES, Usage.Normal | Usage.Position, new Material(ColorAttribute.createDiffuse(Color.RED))),
      1f, 1f, 1f, 10, 10)

    mb.node().id = "box"
    BoxShapeBuilder.build(
      mb.part("box", GL20.GL_TRIANGLES, Usage.Normal | Usage.Position, new Material(ColorAttribute.createDiffuse(Color.BLUE))),
      1f, 1f, 1f)

    mb.node().id = "cone"
    ConeShapeBuilder.build(
      mb.part("cone", GL20.GL_TRIANGLES, Usage.Normal | Usage.Position, new Material(ColorAttribute.createDiffuse(Color.BROWN))),
      1f, 2f, 1f, 10)

    mb.node().id = "capsule"
    CapsuleShapeBuilder.build(
      mb.part("capsule", GL20.GL_TRIANGLES, Usage.Normal | Usage.Position, new Material(ColorAttribute.createDiffuse(Color.MAGENTA))),
      0.5f, 2f, 10)

    mb.end
  }

  def newCollisionObject(cS: btCollisionShape, transform: Matrix4): btCollisionObject = {
    val ret = new btCollisionObject()
    ret.setCollisionShape(cS)
    ret.setWorldTransform(transform)
    ret
  }

  def checkCollision(ds: btDispatcher, obj0: btCollisionObject, obj1: btCollisionObject): Boolean = {

    val co0 = new CollisionObjectWrapper(obj0)
    val co1 = new CollisionObjectWrapper(obj1)

    val ci = new btCollisionAlgorithmConstructionInfo()
    ci.setDispatcher1(ds)
    //    val algorithm = new btSphereBoxCollisionAlgorithm(null, ci, co0.wrapper, co1.wrapper, false)
    //automatically find an algorithm given two wrappers.
    val algorithm = ds.findAlgorithm(co0.wrapper, co1.wrapper)

    val info = new btDispatcherInfo()
    val result = new btManifoldResult(co0.wrapper, co1.wrapper)

    try {
      algorithm.processCollision(co0.wrapper, co1.wrapper, info, result)
      result.getPersistentManifold.getNumContacts > 0
    } finally {
      //Oh man lots of disposals going on here.
      result.dispose()
      info.dispose()
      // Dont have to dispose of the algorithm since we dont "own" it anymore, as it is created internally by dispatcher
      //      algorithm.dispose()
      ci.dispose()
      co1.dispose()
      co0.dispose()
    }
  }

  object GameObject {
    def apply(node: String, model: Model, collisionShape: btCollisionShape): Constructor =
      new Constructor(node, model, collisionShape)

    class Constructor(node: String, model: Model, collisionShape: btCollisionShape) extends Disposable {
      def apply(): GameObject = {
        // lets construct a brand new collision object now
        val body = new btCollisionObject()
        body.setCollisionShape(collisionShape)

        GameObject(new ModelInstance(model, node), body, moving = false)
      }

      override def dispose(): Unit = {
        collisionShape.dispose()
      }
    }

  }

  final case class GameObject(instance: ModelInstance, body: btCollisionObject, moving: Boolean)
    extends Disposable {


    override def dispose(): Unit = {
      body.dispose()
    }
  }

  case class BulletState(model: Model,
                         constructors: mutable.Map[String, GameObject.Constructor],
                         instances: ArrayBuffer[GameObject],
                         collisionConfig: btCollisionConfiguration,
                         dispatcher: btCollisionDispatcher,
                         var spawnTimer: Float
                        ) extends Disposable {

    def spawnRandomObject(): Unit = {
      import MathUtils.random
      val obj = constructors.values.toList(1 + random(constructors.size - 2))().copy(moving = true)
      obj.instance.transform.setFromEulerAngles(random(360f), random(360f), random(360f))
      obj.instance.transform.trn(random(-2.5f, 2.5f), 9f, random(-2.5f, 2.5f))
      obj.body.setWorldTransform(obj.instance.transform)
      instances += obj
    }

    override def dispose(): Unit = {
      //      groundObject.dispose()
      //      groundShape.dispose()
      //
      //      ballObject.dispose()
      //      ballShape.dispose()
      instances.foreach(_.dispose())
      instances.clear()

      constructors.values.foreach(_.dispose())
      constructors.clear

      dispatcher.dispose()
      collisionConfig.dispose()

      model.dispose()
    }
  }

}
