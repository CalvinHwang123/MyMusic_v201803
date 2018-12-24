package calvin.baidumusic.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
/**
 * 音乐处理器，
 * 用来读取音乐路径、时长和
 * 提取音乐的歌曲名、歌手和大小
 * @author Calvin
 *
 */
public class SongInfoProcessor {

	public static void readMusicPath(String dir, String type, List<String> songPathList) {
		File file = new File(dir);
		if(file.exists()){
			File[] files = file.listFiles();
			for(File temp: files){
				if(temp.isDirectory()){
					//递归查找
					readMusicPath(temp.getPath(), type, songPathList);
				}else{
					if (temp.getName().endsWith(type)) {
						songPathList.add(temp.getPath());
					}
				}
			}
		}
	}
	
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
