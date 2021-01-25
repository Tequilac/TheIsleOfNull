package visuals;

import model.items.Item;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TransferableItem implements Transferable
{
    public static final DataFlavor itemFlavor = new DataFlavor(Item.class, "Item object");

    protected static final DataFlavor[] supportedFlavors = { itemFlavor, DataFlavor.stringFlavor };

    private final Item item;

    public TransferableItem(Item item)
    {
        this.item = item;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors()
    {
        return supportedFlavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor dataFlavor)
    {
        return dataFlavor.equals(itemFlavor) || dataFlavor.equals(DataFlavor.stringFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException, IOException
    {
        if(dataFlavor.equals(itemFlavor))
        {
            return item;
        }
        else if(dataFlavor.equals(DataFlavor.stringFlavor))
        {
            return item.toString();
        }
        else
        {
            throw new UnsupportedFlavorException(dataFlavor);
        }
    }
}
