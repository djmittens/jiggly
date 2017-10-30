package me.ngrid.jiggly

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.headless.HeadlessApplication

object BulletSandbox extends ApplicationListener {

  def main(args: Array[String]): Unit = {
    /// I need to start headless libgdx
    /// run a simulation on 2 cubes colliding
    /// record collisions in console.
    new HeadlessApplication(this)
    ()
  }

  override def resume() = {}

  override def pause() = {}

  override def create() = {}

  override def resize(width: Int, height: Int) = {}

  override def dispose() = {}

  override def render() = {
  }
}
