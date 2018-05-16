package me.ngrid.jiggly.bullet

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.headless.mock.audio.MockAudio
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics
import com.badlogic.gdx.backends.headless.mock.input.MockInput
import com.badlogic.gdx.backends.headless.{HeadlessFiles, HeadlessNet}
import com.badlogic.gdx.physics.bullet.Bullet
import com.badlogic.gdx.utils.GdxNativesLoader
import me.ngrid.jiggly.MockGL20

object BulletInit {

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

}
