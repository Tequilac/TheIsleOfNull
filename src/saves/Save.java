package saves;

import entities.*;
import entities.Character;
import entities.Class;
import equipment.Equipment;
import equipment.EquipmentType;
import items.Item;
import items.ItemParser;
import magic.Spell;
import magic.SpellParser;
import main.Game;
import map.*;
import map.districts.AbstractHiddenDistrict;
import map.districts.Dungeon;
import map.districts.Town;
import map.districts.World;
import quests.Quest;
import quests.QuestGiver;
import quests.QuestParser;
import quests.QuestStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

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
                int skillPoints;
                int [] attributes = new int[7];
                Character character;
                Item[] itemsInInventory = new Item[4];
                String itemInInventoryName;

                //position
                posX = Integer.parseInt(br.readLine());
                teamInfo.append(posX).append("\n");
                posY = Integer.parseInt(br.readLine());
                teamInfo.append(posY).append("\n");
                mapDirection = MapDirection.valueOf(br.readLine());
                teamInfo.append(mapDirection).append("\n");

                //info, attributes, equipped items
                for (int i = 0; i < 4; i++)
                {
                    name = br.readLine();
                    teamInfo.append(name).append("\n");
                    characterClass = game.getClass(br.readLine());
                    teamInfo.append(characterClass.getName()).append("\n");
                    race = (game.getRace(br.readLine()));
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
                    skillPoints = Integer.parseInt(br.readLine());
                    teamInfo.append(skillPoints).append("\n");
                    for (int j = 0; j < 7; j++)
                    {
                        attributes[j] = Integer.parseInt(br.readLine());
                        teamInfo.append(attributes[j]).append("\n");
                    }

                    for(int j = 0; j < 4; j++)
                    {
                        itemInInventoryName = br.readLine();
                        if(itemInInventoryName.equals("null"))
                        {
                            itemsInInventory[j] = null;
                            teamInfo.append("null").append("\n");
                        }
                        else
                        {
                            itemsInInventory[j] = ItemParser.parseItem(itemInInventoryName);
                            teamInfo.append(itemsInInventory[j].getName()).append("\n");
                        }
                    }

                    if(characterClass.usesMagic())
                    {
                        int spellAmount = Integer.parseInt(br.readLine());
                        teamInfo.append(spellAmount).append("\n");
                        LinkedList<Spell> spells = new LinkedList<>();
                        String spellName;
                        for(int j = 0; j < spellAmount; j++)
                        {
                            spellName = br.readLine();
                            spells.add(SpellParser.parseSpell(spellName));
                            teamInfo.append(spellName).append("\n");
                        }
                        character = new MagicCharacter(name, health, maxHealth, mana, maxMana, race, level, experience, characterClass,
                                attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5], attributes[6], spells, skillPoints);
                    }
                    else
                    {
                        character = new Character(name, health, maxHealth, race, level, experience, characterClass,
                                attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5], attributes[6], skillPoints);
                    }

                    character.initEquipment(itemsInInventory);

                    characters.add(character);
                }

                //items in inventory
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

                int currentAmount;
                Quest quest;

                //active quests
                int activeQuestsAmount = Integer.parseInt(br.readLine());
                teamInfo.append(activeQuestsAmount).append("\n");
                LinkedList<Quest> activeQuests = new LinkedList<>();
                String activeQuestName;
                for(int i = 0; i < activeQuestsAmount; i++)
                {
                    activeQuestName = br.readLine();
                    teamInfo.append(activeQuestName).append("\n");
                    currentAmount = Integer.parseInt(br.readLine());
                    teamInfo.append(currentAmount).append("\n");
                    quest = QuestParser.parseQuest(activeQuestName, QuestStatus.Active);
                    quest.setCurrentAmount(currentAmount);
                    activeQuests.add(quest);
                }

                //completed quests
                int completedQuestsAmount = Integer.parseInt(br.readLine());
                teamInfo.append(completedQuestsAmount).append("\n");
                LinkedList<Quest> completedQuests = new LinkedList<>();
                String completedQuestName;
                for(int i = 0; i < completedQuestsAmount; i++)
                {
                    completedQuestName = br.readLine();
                    teamInfo.append(completedQuestName).append("\n");
                    completedQuests.add(QuestParser.parseQuest(completedQuestName, QuestStatus.Completed));
                }

                game.setCharacters(characters);
                game.setCurrentCharacter(characters.get(0));
                game.setCurrentMember(0);
                game.setTeam(new Team(new Vector2d(posX, posY), mapDirection, characters, activeQuests, completedQuests, items));
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
            bw.write(String.valueOf(character.getLevel()));
            bw.newLine();
            bw.write(String.valueOf(character.getExperience()));
            bw.newLine();
            bw.write(String.valueOf(character.getSkillPoints()));
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
            for(int i = 0; i < 4; i++)
            {
                bw.write("null");
                bw.newLine();
            }
            if(character instanceof MagicCharacter)
            {
                bw.write(String.valueOf(((MagicCharacter)character).getMana()));
                bw.newLine();
                bw.write(String.valueOf(((MagicCharacter)character).getMaxMana()));
                bw.newLine();
                bw.write("0");
                bw.newLine();
            }
        }
        bw.write("0");
        bw.newLine();
        bw.write("0");
        bw.newLine();
        bw.write("0");
        bw.newLine();
        bw.flush();
    }

    public static void saveCharacters(Game game, File file) throws IOException
    {
        StringBuilder newInfo = new StringBuilder();
        Team team = game.getTeam();

        newInfo.append("C\n");

        //position
        newInfo.append(team.getPosition().getX()).append("\n");
        newInfo.append(team.getPosition().getY()).append("\n");
        newInfo.append(team.getMapDirection()).append("\n");

        //info, attributes, equipped items
        for (Character character : game.getCharacters())
        {
            newInfo.append(character.getName()).append("\n");
            newInfo.append(character.getCharacterClass().getName()).append("\n");
            newInfo.append(character.getRace().getName()).append("\n");
            newInfo.append(character.getHealth()).append("\n");
            newInfo.append(character.getMaxHealth()).append("\n");
            newInfo.append(character.getLevel()).append("\n");
            newInfo.append(character.getExperience()).append("\n");
            newInfo.append(character.getSkillPoints()).append("\n");
            newInfo.append(character.getMight()).append("\n");
            newInfo.append(character.getIntellect()).append("\n");
            newInfo.append(character.getPersonality()).append("\n");
            newInfo.append(character.getEndurance()).append("\n");
            newInfo.append(character.getAccuracy()).append("\n");
            newInfo.append(character.getSpeed()).append("\n");
            newInfo.append(character.getLuck()).append("\n");
            Equipment[] equipments = {character.getHead(), character.getLeftHand(), character.getRightHand(), character.getTorso()};
            for(Equipment equipment : equipments)
            {
                if(equipment.getItem() != null)
                {
                    newInfo.append(equipment.getItem().getName()).append("\n");
                }
                else
                {
                    newInfo.append("null").append("\n");
                }
            }
            if(character instanceof MagicCharacter)
            {
                newInfo.append(((MagicCharacter)character).getMana()).append("\n");
                newInfo.append(((MagicCharacter)character).getMaxMana()).append("\n");
                LinkedList<Spell> spells = ((MagicCharacter) character).getSpells();
                newInfo.append(spells.size()).append("\n");
                for(Spell spell : spells)
                {
                    newInfo.append(spell.getName()).append("\n");
                }
            }
        }

        //items in inventory
        newInfo.append(team.getItemsInInventory().size()).append("\n");
        for(Item item : team.getItemsInInventory())
        {
            newInfo.append(item.getName()).append("\n");
        }

        //active quests
        newInfo.append(team.getActiveQuests().size()).append("\n");
        for(Quest quest : team.getActiveQuests())
        {
            newInfo.append(quest.getName()).append("\n");
            newInfo.append(quest.getCurrentAmount()).append("\n");
        }

        //completed quests
        newInfo.append(team.getCompletedQuests().size()).append("\n");
        for(Quest quest : team.getCompletedQuests())
        {
            newInfo.append(quest.getName()).append("\n");
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
        if (game.getCurrentDistrict() instanceof AbstractHiddenDistrict)
        {
            for(int i = 0; i < game.getCurrentDistrict().getHeight(); i++)
            {
                boolean[][] visTiles = ((AbstractHiddenDistrict) game.getCurrentDistrict()).getVisibleTiles();
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
