package telran.io.service;

import telran.view.InputOutput;

public interface DirectoryFilesCopy {
	/**
	 * 
	 * @param directoryPath
	 * @param maxDepth if < 0, no depth limitation
	 * @param io - InputOutput
	 * Example for max depth 4
	 * Dir. name
	 *     name1 dir
	 *         name11 dir
	 *             name111 dir
	 *                 name1111 file
	 *             name112 file
	 *         name12 file
	 *         name13 file
	 *     name2 file
	 */
	void displayDirectoryContent(String directoryPath, int maxDepth, InputOutput io);
	/**
	 * Copies file from pathFilesSrc to pathFileDest
	 * @param pathFileSrc
	 * @param pathFileDest
	 * @param flOverwrite - if true an existing destination will be overwritten,
	 * otherwise operation will be denied
	 * @return byte rate - number of bytes in one millisecond
	 */
	long copyFiles(String pathFileSrc, String pathFileDest, boolean flOverwrite);
}
