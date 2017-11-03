package me.ngrid.jiggly

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.headless.mock.audio.MockAudio
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics
import com.badlogic.gdx.backends.headless.mock.input.MockInput
import com.badlogic.gdx.backends.headless.{HeadlessFiles, HeadlessNet}
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.{BoxShapeBuilder, SphereShapeBuilder}
import com.badlogic.gdx.graphics.g3d.{Material, Model, ModelInstance}
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.math.{Matrix4, Vector3}
import com.badlogic.gdx.physics.bullet.Bullet
import com.badlogic.gdx.physics.bullet.collision._
import com.badlogic.gdx.utils.{Disposable, GdxNativesLoader}

/**
  * in the spirit of references, this is the tutorial i am following for this class.
  *
  * https://xoppa.github.io/blog/using-the-libgdx-3d-physics-bullet-wrapper-part1/
  *
  */
object BulletSandbox {

  def main(args: Array[String]): Unit = {
    Bullet.init(true)

    GdxNativesLoader.load()
    Gdx.graphics = new MockGraphics()
    Gdx.gl = new MockGL20
    Gdx.gl20 = new MockGL20
    Gdx.files = new HeadlessFiles()
    Gdx.net = new HeadlessNet()
    // the following elements are not applicable for headless applications
    // they are only implemented as mock objects
    Gdx.audio = new MockAudio()
    Gdx.input = new MockInput()

    val st = bulletState

    try {
      while (!checkCollision(st)) {
        import st._

        ball.transform.translate(0f, -0.99f, 0f)
        ballObject.setWorldTransform(ball.transform)
        println("Nope")
      }
      println("Yep")

    } finally {
      bulletState.dispose()
    }

    ()
  }

  def bulletState: BulletState = {
    val model = createModel

    val (ball, ground) = {
      val g = new ModelInstance(model, "ground")
      val b = new ModelInstance(model, "ball")

      b.transform.setToTranslation(0, 9f, 0)
      b -> g
    }

    val ballShape = new btSphereShape(0.5f)
    val groundShape = new btBoxShape(new Vector3(2.5f, 0.5f, 2.5f))

    val (ballObject, groundObject) = {
      newCollisionObject(ballShape, ball.transform) -> newCollisionObject(groundShape, ground.transform)
    }

    val (collisionConfig, dispatcher) = {
      val cc = new btDefaultCollisionConfiguration()
      val d = new btCollisionDispatcher(cc)

      cc -> d
    }

    BulletState(ball, ground, model, ballShape, groundShape, ballObject, groundObject, collisionConfig, dispatcher)
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

    mb.end
  }

  def newCollisionObject(cS: btCollisionShape, transform: Matrix4): btCollisionObject = {
    val ret = new btCollisionObject()
    ret.setCollisionShape(cS)
    ret.setWorldTransform(transform)
    ret
  }

  def checkCollision(st: BulletState): Boolean = {
    import st._
    val co0 = new CollisionObjectWrapper(ballObject)
    val co1 = new CollisionObjectWrapper(groundObject)

    val ci = new btCollisionAlgorithmConstructionInfo()
    ci.setDispatcher1(dispatcher)
    val algorithm = new btSphereBoxCollisionAlgorithm(null, ci, co0.wrapper, co1.wrapper, false)
    val info = new btDispatcherInfo()
    val result = new btManifoldResult(co0.wrapper, co1.wrapper)

    algorithm.processCollision(co0.wrapper, co1.wrapper, info, result)

    val r = result.getPersistentManifold.getNumContacts > 0

    result.dispose()
    info.dispose()
    algorithm.dispose()
    ci.dispose()
    co1.dispose()
    co0.dispose()

    r
  }

  case class BulletState( ball: ModelInstance, ground: ModelInstance,
                          model: Model,
                          ballShape: btCollisionShape,
                          groundShape: btCollisionShape,
                          ballObject: btCollisionObject,
                          groundObject: btCollisionObject,
                          collisionConfig: btCollisionConfiguration,
                          dispatcher: btCollisionDispatcher
                        ) extends Disposable {
    def apply[T](f: BulletState => T): T = {
      try {
        f(this)
      } finally {
        this.dispose()
      }
    }

    override def dispose(): Unit = {
      groundObject.dispose()
      groundShape.dispose()

      ballObject.dispose()
      ballShape.dispose()

      dispatcher.dispose()
      collisionConfig.dispose()

      model.dispose()
    }
  }

}
