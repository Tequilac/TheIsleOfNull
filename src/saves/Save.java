package saves;

import entities.*;
import entities.Character;
import entities.Class;
import items.Item;
import items.ItemParser;
import main.Game;
import map.*;
import map.districts.Dungeon;
import map.districts.Town;
import map.districts.World;
import quests.Quest;
import quests.QuestGiver;

import java.io.*;
import java.util.ArrayList;

public class Save
{
    public static void parseSave(Game game, File file) throws IOException
    {
        parseCharacters(game, file);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String currentDistrict = br.readLine();
        IDistrictParser districtParser;
        switch (currentDistrict.charAt(0))
        {
            case 'W':
                districtParser = new WorldParser();
                break;
            case 'T':
                districtParser = new TownParser();
                break;
            case 'D':
                districtParser = new DungeonParser();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentDistrict.charAt(0));
        }
        districtParser.parseDistrict(game, file);
    }

    public static void parseCharacters(Game game, File file) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder teamInfo = new StringBuilder("C\n");
        ArrayList<Character> characters = new ArrayList<>(4);
        br.readLine();
        while ((line = br.readLine()) != null)
        {
            if(!line.isEmpty() && line.charAt(0) == 'C')
            {
                int posX;
                int posY;
                MapDirection mapDirection;
                String name;
                Class characterClass;
                Race race;
                int health;
                int maxHealth;
                int mana;
                int maxMana;
                int level;
                int experience;
                int [] attributes = new int[7];

                posX = Integer.parseInt(br.readLine());
                teamInfo.append(posX).append("\n");
                posY = Integer.parseInt(br.readLine());
                teamInfo.append(posY).append("\n");
                mapDirection = MapDirection.valueOf(br.readLine());
                teamInfo.append(mapDirection).append("\n");
                for (int i = 0; i < 4; i++)
                {
                    name = br.readLine();
                    teamInfo.append(name).append("\n");
                    characterClass = ClassParser.parseClass(br.readLine() + ".json");
                    teamInfo.append(characterClass.getName()).append("\n");
                    race = RaceParser.parseRace(br.readLine() + ".json");
                    teamInfo.append(race.getName()).append("\n");
                    health = Integer.parseInt(br.readLine());
                    teamInfo.append(health).append("\n");
                    maxHealth = Integer.parseInt(br.readLine());
                    teamInfo.append(maxHealth).append("\n");
                    mana = Integer.parseInt(br.readLine());
                    teamInfo.append(mana).append("\n");
                    maxMana = Integer.parseInt(br.readLine());
                    teamInfo.append(maxMana).append("\n");
                    level = Integer.parseInt(br.readLine());
                    teamInfo.append(level).append("\n");
                    experience = Integer.parseInt(br.readLine());
                    teamInfo.append(experience).append("\n");
                    for (int j = 0; j < 7; j++)
                    {
                        attributes[j] = Integer.parseInt(br.readLine());
                        teamInfo.append(attributes[j]).append("\n");
                    }
                    characters.add(new Character(name, health, maxHealth, mana, maxMana, race, level, experience, characterClass,
                            attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5], attributes[6]));
                }

                int itemsAmount = Integer.parseInt(br.readLine());
                teamInfo.append(itemsAmount).append("\n");
                ArrayList<Item> items = new ArrayList<>(itemsAmount);
                String itemName;
                for(int i = 0; i < itemsAmount; i++)
                {
                    itemName = br.readLine();
                    teamInfo.append(itemName).append("\n");
                    items.add(ItemParser.parseItem(itemName));
                }

                game.setCharacters(characters);
                game.setCurrentMember(0);
                game.setTeam(new Team(new Vector2d(posX, posY), mapDirection, characters, items));
                game.setTeamInfo(teamInfo.toString());
            }
        }
    }

    public static void saveGame(Game game, File file) throws IOException
    {
        if(file.exists())
        {
            saveCharacters(game, file);
            saveCurrentDistrict(game, file);
        }
        else
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(game.getCurrentDistrict().getName());
            bw.newLine();
            bw.flush();
            saveInitialCharacters(game, file);
            saveInitialDistricts(game, file);
            parseSave(game, file);
        }
    }

    private static void saveInitialCharacters(Game game, File file) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write("C");
        bw.newLine();
        bw.write(String.valueOf(game.getTeam().getPosition().getX()));
        bw.newLine();
        bw.write(String.valueOf(game.getTeam().getPosition().getY()));
        bw.newLine();
        bw.write(String.valueOf(game.getTeam().getMapDirection()));
        bw.newLine();
        for (Character character : game.getCharacters())
        {
            bw.write(character.getName());
            bw.newLine();
            bw.write(character.getCharacterClass().getName());
            bw.newLine();
            bw.write(character.getRace().getName());
            bw.newLine();
            bw.write(String.valueOf(character.getHealth()));
            bw.newLine();
            bw.write(String.valueOf(character.getMaxHealth()));
            bw.newLine();
            bw.write(String.valueOf(character.getMana()));
            bw.newLine();
            bw.write(String.valueOf(character.getMaxMana()));
            bw.newLine();
            bw.write(String.valueOf(character.getLevel()));
            bw.newLine();
            bw.write(String.valueOf(character.getExperience()));
            bw.newLine();
            bw.write(String.valueOf(character.getMight()));
            bw.newLine();
            bw.write(String.valueOf(character.getIntellect()));
            bw.newLine();
            bw.write(String.valueOf(character.getPersonality()));
            bw.newLine();
            bw.write(String.valueOf(character.getEndurance()));
            bw.newLine();
            bw.write(String.valueOf(character.getAccuracy()));
            bw.newLine();
            bw.write(String.valueOf(character.getSpeed()));
            bw.newLine();
            bw.write(String.valueOf(character.getLuck()));
            bw.newLine();
        }
        bw.write("0");
        bw.newLine();
        bw.flush();
    }

    public static void saveCharacters(Game game, File file) throws IOException
    {
        StringBuilder newInfo = new StringBuilder();

        newInfo.append("C\n");
        newInfo.append(game.getTeam().getPosition().getX()).append("\n");
        newInfo.append(game.getTeam().getPosition().getY()).append("\n");
        newInfo.append(game.getTeam().getMapDirection()).append("\n");
        for (Character character : game.getCharacters())
        {
            newInfo.append(character.getName()).append("\n");
            newInfo.append(character.getCharacterClass().getName()).append("\n");
            newInfo.append(character.getRace().getName()).append("\n");
            newInfo.append(character.getHealth()).append("\n");
            newInfo.append(character.getMaxHealth()).append("\n");
            newInfo.append(character.getMana()).append("\n");
            newInfo.append(character.getMaxMana()).append("\n");
            newInfo.append(character.getLevel()).append("\n");
            newInfo.append(character.getExperience()).append("\n");
            newInfo.append(character.getMight()).append("\n");
            newInfo.append(character.getIntellect()).append("\n");
            newInfo.append(character.getPersonality()).append("\n");
            newInfo.append(character.getEndurance()).append("\n");
            newInfo.append(character.getAccuracy()).append("\n");
            newInfo.append(character.getSpeed()).append("\n");
            newInfo.append(character.getLuck()).append("\n");
        }
        newInfo.append(game.getTeam().getItemsInInventory().size()).append("\n");
        for(Item item : game.getTeam().getItemsInInventory())
        {
            newInfo.append(item.getName()).append("\n");
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder inputBuffer = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            inputBuffer.append(line);
            inputBuffer.append('\n');
        }
        br.close();
        String inputStr = inputBuffer.toString();
        inputStr = inputStr.replace(game.getTeamInfo(), newInfo.toString());
        FileOutputStream fileOut = new FileOutputStream(file);
        fileOut.write(inputStr.getBytes());
        fileOut.close();
    }

    public static void saveInitialDistricts(Game game, File file) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

        String[] types = {"W", "T", "D"};

        for(String type : types)
        {
            for (int i = 1; i < 6; i++)
            {
                for (int j = 1; j < 6; j++)
                {
                    bw.write(type+i+j);
                    bw.newLine();
                    bw.write("NV");
                    bw.newLine();
                }
            }
        }

        bw.close();
    }

    public static void saveCurrentDistrict(Game game, File file) throws IOException
    {
        StringBuilder newInfo;

        newInfo = new StringBuilder(game.getCurrentDistrict().getName() + "\n");
        newInfo.append("V").append("\n");
        if (game.getCurrentDistrict() instanceof  World || game.getCurrentDistrict() instanceof Dungeon)
        {
            for(int i = 0; i < game.getCurrentDistrict().getHeight(); i++)
            {
                boolean[][] visTiles = ((World) game.getCurrentDistrict()).getVisibleTiles();
                for(int j = 0; j < game.getCurrentDistrict().getWidth(); j++)
                {
                    if(visTiles[j][i])
                        newInfo.append("1");
                    else
                        newInfo.append("0");
                }
                newInfo.append("\n");
            }


            if(game.getCurrentDistrict().getEnemies() != null)
            {
                newInfo.append(game.getCurrentDistrict().getEnemies().size()).append("\n");

                for(Group group : game.getCurrentDistrict().getEnemies())
                {
                    newInfo.append(group.getEntities().get(0).getName()).append("\n");
                    newInfo.append(group.getEntities().get(0).getRace().getName()).append("\n");
                    newInfo.append(group.getEntities().size()).append("\n");
                    newInfo.append(group.getEntities().get(0).getLevel()).append("\n");
                    newInfo.append(group.getEntities().get(0).getExperience()).append("\n");
                    newInfo.append(group.getPosition().getX()).append("\n");
                    newInfo.append(group.getPosition().getY()).append("\n");
                    newInfo.append(group.getMapDirection()).append("\n");
                    for(Entity enemy : group.getEntities())
                    {
                        newInfo.append(enemy.getHealth()).append("\n");
                    }
                }
            }
        }


        if(game.getCurrentDistrict().getChests() != null)
        {
            newInfo.append(game.getCurrentDistrict().getChests().size()).append("\n");

            for (Chest chest : game.getCurrentDistrict().getChests())
            {
                newInfo.append(chest.getGold()).append("\n");
                newInfo.append(chest.getItems().size()).append("\n");
                for (Item item : chest.getItems())
                {
                    newInfo.append(item.getName()).append("\n");
                }
            }
        }

        if(game.getCurrentDistrict() instanceof World || game.getCurrentDistrict() instanceof Town)
        {

            if(game.getCurrentDistrict().getQuestGivers() != null)
            {
                for (QuestGiver questGiver : game.getCurrentDistrict().getQuestGivers())
                {
                    for (Quest quest : questGiver.getQuests())
                    {
                        newInfo.append(quest.getQuestStatus()).append("\n");
                    }
                }
            }
        }

        newInfo.setLength(newInfo.length() - 1);

        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder inputBuffer = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            inputBuffer.append(line);
            inputBuffer.append('\n');
        }
        br.close();
        String inputStr = inputBuffer.toString();
        inputStr = inputStr.replace(game.getCurrentDistrict().getLoadedInfo(), newInfo.toString());
        FileOutputStream fileOut = new FileOutputStream(file);
        fileOut.write(inputStr.getBytes());
        fileOut.close();
    }

    public static void setCurrentDistrict(Game game, File file, String newMap, Vector2d position, MapDirection direction) throws IOException
    {
        StringBuilder oldInfo = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        for (int i = 0; i < 5; i++)
        {
            oldInfo.append(br.readLine()).append("\n");
        }
        String newInfo = newMap + "\nC\n" + position.getX() + "\n" + position.getY() + "\n" + direction + "\n";
        br = new BufferedReader(new FileReader(file));
        StringBuilder inputBuffer = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            inputBuffer.append(line);
            inputBuffer.append('\n');
        }
        br.close();
        String inputStr = inputBuffer.toString();
        inputStr = inputStr.replaceFirst(oldInfo.toString(), newInfo);
        FileOutputStream fileOut = new FileOutputStream(file);
        fileOut.write(inputStr.getBytes());
        fileOut.close();
    }
}
