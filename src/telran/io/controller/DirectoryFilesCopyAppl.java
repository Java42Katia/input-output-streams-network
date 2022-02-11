package telran.io.controller;

import telran.io.service.DirectoryFilesCopyImpl;
import telran.view.ConsoleInputOutput;
import telran.view.Menu;

public class DirectoryFilesCopyAppl {

	public static void main(String[] args) {
		Menu menu = new Menu("DirectoryFilesCopy menu", DirectoryFilesCopyActions.getItems(new DirectoryFilesCopyImpl()));
		menu.perform(new ConsoleInputOutput());
	}

}
