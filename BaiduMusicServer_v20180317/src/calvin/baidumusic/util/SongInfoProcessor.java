package calvin.baidumusic.util;

import java.io.File;
import java.text.DecimalFormat;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;

/**
 * 音乐信息处理器，
 * 用来读取歌曲名、歌手名
 * 文件大小和时长
 * @author Calvin
 *
 */
public class SongInfoProcessor {
	
	public static String readSongName(String rawStr) {
		String rawSongName = rawStr.split("-")[0];
		int index = rawSongName.lastIndexOf("\\");
		return rawSongName.substring(index + 1);
	}
	
	public static String readSinger(String rawStr) {
		String rawSinger = rawStr.split("-")[1];
		int singerEndIndex = rawSinger.indexOf(".mp3");
		return rawSinger.substring(0, singerEndIndex);
	}
	
	public static String readSize(String filePath) {
		return new DecimalFormat("#.00").format(new File(filePath).length() / 1024.0 / 1024.0) + "MB";
	}
	
	public static String readDuration(String filePath) {
		Encoder encoder = new Encoder();
		long duration = 0;
		try {
			duration = encoder.getInfo(new File(filePath)).getDuration();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		return TimeUtil.formatTime(duration);
	}
}
