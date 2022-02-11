package telran.io.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import telran.io.service.DirectoryFilesCopy;
import telran.view.InputOutput;
import telran.view.Item;

public class DirectoryFilesCopyActions {
	
	private static DirectoryFilesCopy directoryFilesCopy;
	private static ArrayList<Item> items;
	
	public static ArrayList<Item> getItems(DirectoryFilesCopy dfc) {
		DirectoryFilesCopyActions.directoryFilesCopy = dfc;
		// V.R. The following check isn't necessary. The method getItems() is called once only.
		if (items == null) {
			items = new ArrayList<Item>(List.of(
					Item.of("Display directory content", DirectoryFilesCopyActions::displayDirectoryContent), 
					Item.of("File copy", DirectoryFilesCopyActions::copyFile),
					Item.exit()));
		}
		return  items;
	}
	
	private static void displayDirectoryContent(InputOutput io) {
		/* V.R. It is possible (and necessary) to implement this logic using
		 * Input/Output features only. For example: 
			int maxDepth = io.readInt("Enter depth (any integer, if maxDepth < 0 , no depth limitation");
			String directoryPath = io.readStringPredicate("Enter directory path", "Not directory path",
				str -> new File(str).isDirectory());
		 *  It is more simple and clear
		 */
		String pathDir = io.readString("Enter path to directory");
		File dir = new File(pathDir);
		if (!dir.exists()) io.writeObjectLine("Directory not found");
		else if (dir.isFile()) io.writeObjectLine("It is not a directory");
		else {
			int maxDepth = io.readInt("Enter max depth");
			directoryFilesCopy.displayDirectoryContent(pathDir, maxDepth, io);
		}
	}
	
	private static void copyFile(InputOutput io) {
		/* V.R. It is possible (and necessary) to implement this logic using
		 * Input/Output features only. For example: 
			String pathFileSrc = io.readStringPredicate("Enter source path", "Not file path",
					str -> new File(str).isFile());
			String pathFileDest = io.readStringPredicate("Enter destination path", " ",
				str -> {
					File dest = new File(str);
					return dest.isFile() || !dest.exists();
				});
			boolean flOverwrite = io.readString("overwrite file? "+ "Y/N").matches("[Yy][a-zA-Z]*")? true: false;
		 *  It is more simple and clear
		 */
		String pathSrcFile = io.readString("Enter path to source file");
		File srcFile = new File(pathSrcFile);
		if (!srcFile.exists()) {
			io.writeObjectLine("File not found");
			return;
		}
		String pathDestFile = io.readString("Enter path to destination file");
		File destFile = new File(pathDestFile);
		if (destFile.exists() && io.readString("Destination file exists, do you want to overwrite it? Y|n").matches("n|N|no")) io.writeObjectLine("Aborted");
		else io.writeObjectLine(String.format("byte rate %d/millisec", directoryFilesCopy.copyFiles(pathSrcFile, pathDestFile, true)));
	}
}
