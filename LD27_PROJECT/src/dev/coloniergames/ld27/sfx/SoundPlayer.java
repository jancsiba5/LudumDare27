package dev.coloniergames.ld27.sfx;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class SoundPlayer {

	/**
	 * 
	 * @param sX: source X
	 * @param sY: source Y
	 * @param lX: listener X
	 * @param lY: listener Y
	 */
	public static void playSound(String path, float sX, float sY, float lX, float lY, boolean looping) {

		/** SOUND BUFFER */
		IntBuffer buffer = BufferUtils.createIntBuffer(1);

		/** SOURCE BUFFER */
		IntBuffer source = BufferUtils.createIntBuffer(1);


		/** SOURCE POSITION */
		FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3).put(new float[] { sX, sY, 0.0f });

		/** SOURCE VELOCITY */
		FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0f, 0f, 0f });

		/** LISTENER POSITION */
		FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { lX, lY, 0.0f});

		/** LISTENER VELOCITY */
		FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0f, 0f, 0f });

		/** Orientation of the listener. (first 3 elements are "at", second 3 are "up") */
		FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] 
				{ 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f });


		sourcePos.flip();
		sourceVel.flip();
		listenerPos.flip();
		listenerVel.flip();
		listenerOri.flip();

		/** LOAD SOUND TO BE PLAYED */

		AL10.alGenBuffers(buffer);

		if(AL10.alGetError() != AL10.AL_NO_ERROR) {
			System.out.println("PROBLEM!!!");
		}

		AudioInputStream ain = null;
		WaveData waveFile = null;

		try {
			
			ain = AudioSystem.getAudioInputStream(new File(path));
			
			waveFile = WaveData.create(ain);
			
		} catch(java.io.FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

		System.out.println("WAVEFILE = " + waveFile);

		AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);

		waveFile.dispose();

		AL10.alGenSources(source);

		AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
		AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
		AL10.alSourcef(source.get(0), AL10.AL_GAIN, 1.0f);
		AL10.alSource(source.get(0), AL10.AL_POSITION, sourcePos);
		AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel);
		if(looping) AL10.alSourcei(source.get(0), AL10.AL_LOOPING, AL10.AL_TRUE);
		else AL10.alSourcei(source.get(0), AL10.AL_LOOPING, AL10.AL_FALSE);

		AL10.alListener(AL10.AL_POSITION, listenerPos);
		AL10.alListener(AL10.AL_VELOCITY, listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);

		AL10.alSourcePlay(source.get(0));
	}
	
	public static void playSound(String path, float sX, float sY, float lX, float lY, float volume, float pitch, boolean looping) {

		/** SOUND BUFFER */
		IntBuffer buffer = BufferUtils.createIntBuffer(1);

		/** SOURCE BUFFER */
		IntBuffer source = BufferUtils.createIntBuffer(1);


		/** SOURCE POSITION */
		FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3).put(new float[] { sX, sY, 0.0f });

		/** SOURCE VELOCITY */
		FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0f, 0f, 0f });

		/** LISTENER POSITION */
		FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { lX, lY, 0.0f});

		/** LISTENER VELOCITY */
		FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0f, 0f, 0f });

		/** Orientation of the listener. (first 3 elements are "at", second 3 are "up") */
		FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] 
				{ 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f });


		sourcePos.flip();
		sourceVel.flip();
		listenerPos.flip();
		listenerVel.flip();
		listenerOri.flip();

		/** LOAD SOUND TO BE PLAYED */

		AL10.alGenBuffers(buffer);

		if(AL10.alGetError() != AL10.AL_NO_ERROR) {
			System.out.println("PROBLEM!!!");
		}

		AudioInputStream ain = null;
		WaveData waveFile = null;

		try {
			
			ain = AudioSystem.getAudioInputStream(new File(path));
			
			waveFile = WaveData.create(ain);
			
		} catch(java.io.FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

		System.out.println("WAVEFILE = " + waveFile);

		AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);

		waveFile.dispose();

		AL10.alGenSources(source);

		AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
		AL10.alSourcef(source.get(0), AL10.AL_PITCH, pitch);
		AL10.alSourcef(source.get(0), AL10.AL_GAIN, volume);
		AL10.alSource(source.get(0), AL10.AL_POSITION, sourcePos);
		AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel);

		AL10.alListener(AL10.AL_POSITION, listenerPos);
		AL10.alListener(AL10.AL_VELOCITY, listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);

		AL10.alSourcePlay(source.get(0));
	}

}
