package user.implementation;

import user.service.PlayAVIService;

public class PlayAVIServiceImpl implements PlayAVIService {

	public String getAviPath(String path, String MD5, String postfix) {
		
		String avipath = path + MD5 + postfix;
		return avipath;
	}

}
