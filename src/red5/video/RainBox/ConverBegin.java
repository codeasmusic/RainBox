package red5.video.RainBox;

import java.net.MalformedURLException;

import red5.video.filepath.ClassLoaderUtil;

public class ConverBegin {
	public static boolean tag = true;
	private String folderpath;
	private String fileName;

	public ConverBegin(String name) {
		fileName = name;
	}

	public boolean transCode() {
		String othersfolder = null;
		try {
			othersfolder = ClassLoaderUtil.getExtendResource("../others/")
					.toString();
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		folderpath = othersfolder.substring(6, othersfolder.lastIndexOf("/"))
				+ "/";
		folderpath = folderpath.replace("%20", " ");
		folderpath = folderpath.replace("/", "\\\\");
		String path = folderpath + fileName;
		try {
			ConverVideo cv = new ConverVideo(path);
			cv.beginConver();
			return true;
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
}
