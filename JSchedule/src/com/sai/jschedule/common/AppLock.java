package com.sai.jschedule.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class AppLock {

	private FileLock lock;
	private FileChannel channel;

	@SuppressWarnings("resource")
	public boolean isAppActive() throws IOException, FileNotFoundException {
		try{
			File file = new File(System.getProperty("user.home"), "app.tmp");
			channel = new RandomAccessFile(file, "rw").getChannel();
			lock = channel.tryLock();
			if(lock == null){
				channel.close();
				return true;
			}

			Runtime.getRuntime().addShutdownHook(new Thread(){
				public void run(){
					try {
						lock.release();
						channel.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
