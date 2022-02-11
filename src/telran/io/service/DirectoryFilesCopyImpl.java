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
		//[YG] flOverwrite should be considered only if destination file doesn't exist
		if (!flOverwrite) return 0; //[YG] returning 0 will confuse the user (not clear why byte rate is 0)
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
				//[YG] it's bug, because a read may take less than 1024 bytes, but you write 1024. It means that the garbage may be written in destination
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
					//[YG] number of the File System nodes may be a pretty big, so better to use StringBuilder for concatination rather than += of strings
					str += tab.repeat(level) + String.format("|__ %s:  dir\n", f.getName());
					if (level < maxDepth - 1) {
						//[YG] Using level + 1 instead of ++level much better, as you won't need to do ++ and then --
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
