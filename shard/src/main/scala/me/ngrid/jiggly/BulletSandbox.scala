package me.ngrid.jiggly


import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.g3d.{Material, Model, ModelInstance}
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.math.{Matrix4, Vector3}
import com.badlogic.gdx.physics.bullet.Bullet
import com.badlogic.gdx.physics.bullet.collision._

/**
  * in the spirit of references, this is the tutorial i am following for this class.
  *
  * https://xoppa.github.io/blog/using-the-libgdx-3d-physics-bullet-wrapper-part1/
  *
  */
object BulletSandbox {

  def main(args: Array[String]): Unit = {
    Bullet.init(true)

    val model = createModel
    val (ball, ground) = modelInstances(model)


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

    groundObject.dispose()
    groundShape.dispose()

    ballObject.dispose()
    ballShape.dispose()

    dispatcher.dispose()
    collisionConfig.dispose()

    model.dispose()
  }

  def modelInstances(m: Model): (ModelInstance, ModelInstance) = {
    val g = new ModelInstance(m, "ground")
    val b = new ModelInstance(m, "ball")

    b.transform.setToTranslation(0, 9f, 0)
    b -> g
  }

  def createModel: Model = {

    val mb = new ModelBuilder
    mb.begin()
    mb.node().id = "ground"
    mb.createBox(5f, 1f,5f, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Normal | Usage.Position)
    mb.node().id = "ball"
    mb.createSphere(1f, 1f, 1f, 10, 10, new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Normal | Usage.Position)

    mb.end
  }

  def newCollisionObject(cS: btCollisionShape, transform: Matrix4): btCollisionObject = {
    val ret = new btCollisionObject()
    ret.setCollisionShape(cS)
    ret.setWorldTransform(transform)
    ret
  }

  def checkCollision(bO: btCollisionObject, gO: btCollisionObject): Boolean = {
    val co0 = new CollisionObjectWrapper(bO)
    val co1 = new CollisionObjectWrapper(gO)

    val ci = new btCollisionAlgorithmConstructionInfo()
//    ci.setDispatcher1(dispatcher)
  }

  case class BulletState (

                         )
}
