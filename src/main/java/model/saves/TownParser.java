package model.saves;

import model.game.Game;
import model.map.*;
import model.map.districts.Town;
import model.quests.Quest;
import model.quests.QuestGiver;
import model.quests.QuestStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class TownParser implements IDistrictParser
{
    @Override
    public void parseDistrict(Game game, File file) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String currentDistrict = br.readLine();
        StringBuilder currentDistrictInfo = new StringBuilder(currentDistrict + "\n");
        String line = br.readLine();

        Town district = (Town) MapParser.parseMap(currentDistrict);

        while (!line.equals(currentDistrict))
        {
            line = br.readLine();
        }
        String visited = br.readLine();
        currentDistrictInfo.append(visited);
        if(visited.equals("V"))
        {
            if(district.getChests() != null)
            {
                int chestsNumber = Integer.parseInt(br.readLine());
                String itemName;
                int gold;
                int itemAmount;
                currentDistrictInfo.append("\n").append(chestsNumber);
                ArrayList<Chest> chests = district.getChests();
                if(chestsNumber != 0)
                {
                    for(int i = 0; i < chestsNumber; i++)
                    {
                        Chest chest = chests.get(i);
                        gold = Integer.parseInt(br.readLine());
                        currentDistrictInfo.append("\n").append(gold);
                        chest.setGold(gold);
                        itemAmount = Integer.parseInt(br.readLine());
                        currentDistrictInfo.append("\n").append(itemAmount);
                        LinkedList<String> items = new LinkedList<>();
                        for(int j = 0; j < itemAmount; j++)
                        {
                            itemName = br.readLine();
                            currentDistrictInfo.append("\n").append(itemName);
                            items.add(itemName);
                        }
                        chest.getItems().removeIf(item -> !items.contains(item.getName()));
                    }
                }
            }

            ArrayList<QuestGiver> questGivers = district.getQuestGivers();
            QuestStatus questStatus;
            if (questGivers != null)
            {
                for (QuestGiver questGiver : questGivers)
                {
                    ArrayList<Quest> quests = questGiver.getQuests();
                    for (Quest quest : quests)
                    {
                        questStatus = QuestStatus.valueOf(br.readLine());
                        currentDistrictInfo.append("\n").append(questStatus);
                        quest.setQuestStatus(questStatus);
                    }
                }
            }
        }
//        game.setCurrentDistrict(district);
//        game.getCurrentDistrict().setLoadedInfo(currentDistrictInfo.toString());
    }
}
