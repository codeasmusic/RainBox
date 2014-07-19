package red5.video.RainBox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import red5.video.filepath.ClassLoaderUtil; //相对路径查询帮助类
/* 
 * Title:FLV视频转换类 
 * @author ljz 
 * ClassVer:0.1.001.2010.04.14 
 */

public class ConverVideo {
	private Date dt;
	private long begintime;
	private String PATH;
	private String filerealname; // 文件名 不包括扩展名
	private String filename; // 包括扩展名
	private String folderpath; // flv视频的目录
	private String ffmpegpath; // ffmpeg.exe的目录

	public ConverVideo() {
	}

	public ConverVideo(String path) {
		PATH = path;
	}

	public String getPATH() {
		return PATH;
	}

	public void setPATH(String path) {
		PATH = path;
	}

	public boolean beginConver() throws MalformedURLException {
	    String flvfolder=ClassLoaderUtil.getExtendResource("../flv/").toString(); // flv视频的目录
		folderpath = flvfolder.substring(6,flvfolder.lastIndexOf("/"))+"/";
		folderpath = folderpath.replace("%20"," ");
	    folderpath = folderpath.replace("/","\\\\");
	    ffmpegpath =folderpath+"ffmpeg/ffmpeg.exe"; // ffmpeg.exe的目录
	    ffmpegpath = ffmpegpath.replace("/","\\\\");
		File fi = new File(PATH);
		filename = fi.getName();
		filerealname = filename.substring(0, filename.lastIndexOf("."))
				.toLowerCase();

		if (!checkfile(PATH)) {
			return false;
		}
		dt = new Date();
		begintime = dt.getTime();
		if (process()) {
			Date dt2 = new Date();
			long endtime = dt2.getTime();
			long timecha = (endtime - begintime);
			String totaltime = sumTime(timecha);
			PATH = null;
			return true;
		} else {
			PATH = null;
			return false;
		}
	}

	private boolean process() {
		int type = checkContentType();
		boolean status = false;
		if (type == 0) {
			// 直接将文件转为flv文件
			status = processFLV(PATH);
		}
		return status;
	}

	private int checkContentType() {
		String type = PATH.substring(PATH.lastIndexOf(".") + 1, PATH.length())
				.toLowerCase();
		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 1;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		} else if (type.equals("flv")) {
			return 0;
		}
		return 9;
	}

	private boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		} else {
			return true;
		}
	}

	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	@SuppressWarnings("unchecked")
	private boolean processFLV(String oldfilepath) {
		if (!checkfile(PATH)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}
		List commend = new java.util.ArrayList();
		commend.add(ffmpegpath);
		commend.add("-i");
		commend.add(oldfilepath);
		commend.add("-ab");
		commend.add("64");
		commend.add("-strict");
		commend.add("-2");
		commend.add("-vcodec");
		commend.add("h264");
		commend.add("-ac");
		commend.add("2");
		commend.add("-ar");
		commend.add("24000");
		commend.add("-qscale");
		commend.add("6");
		commend.add("-r");
		commend.add("29.97");
		commend.add("-y");
		commend.add(folderpath + filerealname + ".mp4");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int doWaitFor(Process p) {
		InputStream in = null;
		InputStream err = null;
		int exitValue = -1; // returned to caller when p is finished
		try {
			System.out.println("comeing");
			in = p.getInputStream();
			err = p.getErrorStream();
			boolean finished = false; // Set to true when p is finished
			while (!finished) {
				try {
					while (in.available() > 0) {
						// Print the output of our system call
						Character c = new Character((char) in.read());
						System.out.print(c);
					}
					while (err.available() > 0) {
						// Print the output of our system call
						Character c = new Character((char) err.read());
						System.out.print(c);
					}
					// Ask the process for its exitValue. If the process
					// is not finished, an IllegalThreadStateException
					// is thrown. If it is finished, we fall through and
					// the variable finished is set to true.
					exitValue = p.exitValue();
					finished = true;
				} catch (IllegalThreadStateException e) {
					// Process is not finished yet;
					// Sleep a little to save on CPU cycles
					Thread.currentThread().sleep(500);
				}
			}
		} catch (Exception e) {
			// unexpected exception! print it out for debugging...
			System.err.println("doWaitFor();: unexpected exception - "
					+ e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			if (err != null) {
				try {
					err.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		// return completion status to caller
		return exitValue;
	}
	

	public String sumTime(long ms) {
		int ss = 1000;
		long mi = ss * 60;
		long hh = mi * 60;
		long dd = hh * 24;
		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second
				* ss;
		String strDay = day < 10 ? "0" + day + "天" : "" + day + "天";
		String strHour = hour < 10 ? "0" + hour + "小时" : "" + hour + "小时";
		String strMinute = minute < 10 ? "0" + minute + "分" : "" + minute + "分";
		String strSecond = second < 10 ? "0" + second + "秒" : "" + second + "秒";
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : ""
				+ milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond + "毫秒" : ""
				+ strMilliSecond + " 毫秒";
		return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " "
				+ strMilliSecond;
	}
}
