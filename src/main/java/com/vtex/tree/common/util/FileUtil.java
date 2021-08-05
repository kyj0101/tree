package com.vtex.tree.common.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	public static File getRenamedFile(String saveDirectory, String oldName) {

		File newFile = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
		DecimalFormat decimalFormat = new DecimalFormat("000");

		int dot = oldName.lastIndexOf(".");
		String extension = dot > -1 ? oldName.substring(dot) : "";
		String newName = simpleDateFormat.format(new Date()) + decimalFormat.format(Math.random() * 999) + extension;

		newFile = new File(saveDirectory, newName);

		return newFile;
	}

	public static Map<String, Object> getFileMap(MultipartFile upFile, 
													String saveDirectory, 
													int fileId, 
													int fileSn) throws IOException {

		File dir = new File(saveDirectory);
		Map<String, Object> fileMap = new HashMap<>();

		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {

			if (upFile.isEmpty()) {
				return null;
			}

			File renameFile = FileUtil.getRenamedFile(saveDirectory, upFile.getOriginalFilename());

			upFile.transferTo(renameFile);

			fileMap.put("originalName", upFile.getOriginalFilename());
			fileMap.put("reNamed", renameFile.getName());
			fileMap.put("fileSn", fileSn);
			fileMap.put("fileId", fileId);
			fileMap.put("fileStore", saveDirectory);
			
			return fileMap;

		} catch (IllegalStateException | IOException e) {
			throw e;
		}
	}
	
	public static void deleteOneFile(String saveDirectory, String renamedFile) {

		File dir = new File(saveDirectory, renamedFile);
		dir.delete();
	}
}
