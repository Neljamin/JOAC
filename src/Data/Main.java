package Data;

public class Main {

	public static void main(String[] args) {
		ColourTable colour_table = new ColourTable("colours.txt");
		ColourCatalogue colour_catalogue = new ColourCatalogue(colour_table);
		TeamEndingsMap team_endings_map = new TeamEndingsMap("newTeamEndings.txt");
		TeamNicknameMap team_nickname_map = new TeamNicknameMap();
		colour_catalogue.print_colours_to_files();
		team_endings_map.printMapToFile();
		team_nickname_map.printMapToFile();

	}

}
