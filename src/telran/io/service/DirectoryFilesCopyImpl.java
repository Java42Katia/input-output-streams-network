package telran.io.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import telran.view.InputOutput;

public class DirectoryFilesCopyImpl implements DirectoryFilesCopy {

	@Override
	public void displayDirectoryContent(String directoryPath, int maxDepth, InputOutput io) {
		io.writeObjectLine(directoryPath);
		io.writeObjectLine(getDirectoryContent(new File(directoryPath), maxDepth, 0));
	}

	@Override
	public long copyFiles(String pathFileSrc, String pathFileDest, boolean flOverwrite) {
		if (!flOverwrite) return 0;
		InputStream is;
		OutputStream os;
		try {
			is = new FileInputStream(pathFileSrc);
			os = new FileOutputStream(pathFileDest);
			byte[] buffer = new byte[1024];
			int bytes = 0;
			long totalBytes = 0;
			long startTime = System.currentTimeMillis();
			while (bytes >= 0) {
				bytes = is.read(buffer, 0, 1024);
				os.write(buffer);
				totalBytes += bytes; 
			}
			long endTime = System.currentTimeMillis();
			is.close();
			os.close();
			return endTime != startTime? totalBytes/(endTime - startTime) : totalBytes;
		} catch (IOException e) {
			return 0;
		}
		
		
	}
	
	private String getDirectoryContent(File file, int maxDepth, int level) {
		String str = "";
		String tab = "    ";
		if (file.listFiles() != null) {
			for (File f : file.listFiles()) {
				if (f.isDirectory()) {
					str += tab.repeat(level) + String.format("|__ %s:  dir\n", f.getName());
					if (level < maxDepth - 1) {
						str += getDirectoryContent(f, maxDepth, ++level);
						--level;
					}
				} else 
					str += tab.repeat(level) + String.format("|__ %s:  file\n", f.getName());
			} 
		}
		return str;
	}

}
