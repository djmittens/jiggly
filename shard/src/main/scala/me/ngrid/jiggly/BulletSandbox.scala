package me.ngrid.jiggly


import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.g3d.{Material, Model, ModelInstance}
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.math.{Matrix4, Vector3}
import com.badlogic.gdx.physics.bullet.Bullet
import com.badlogic.gdx.physics.bullet.collision._
import com.badlogic.gdx.utils.Disposable

/**
  * in the spirit of references, this is the tutorial i am following for this class.
  *
  * https://xoppa.github.io/blog/using-the-libgdx-3d-physics-bullet-wrapper-part1/
  *
  */
object BulletSandbox {

  def main(args: Array[String]): Unit = {
    Bullet.init(true)

    bulletState(checkCollision)

    ()
  }

  def bulletState[T]: BulletState = {
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

    BulletState(model, ballShape, groundShape, ballObject, groundObject, collisionConfig, dispatcher)
  }


  def createModel: Model = {

    val mb = new ModelBuilder
    mb.begin()
    mb.node().id = "ground"
    mb.createBox(5f, 1f, 5f, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Normal | Usage.Position)
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

  def checkCollision(st: BulletState): Boolean = {
    import st._
    val co0 = new CollisionObjectWrapper(ballObject)
    val co1 = new CollisionObjectWrapper(groundObject)

    val ci = new btCollisionAlgorithmConstructionInfo()
        ci.setDispatcher1(dispatcher)

    false
  }

  case class BulletState(
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
