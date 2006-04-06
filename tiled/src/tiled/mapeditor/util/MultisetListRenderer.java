/*
 *  Tiled Map Editor, (c) 2004
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Adam Turk <aturk@biggeruniverse.com>
 *  Bjorn Lindeijer <b.lindeijer@xs4all.nl>
 *  
 *  modified for Stendhal, an Arianne powered RPG 
 *  (http://arianne.sf.net)
 *
 *  Matthias Totz <mtotz@users.sourceforge.net>
 */

package tiled.mapeditor.util;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;

import tiled.core.*;
import tiled.mapeditor.MapEditor;
import tiled.core.Map;

public class MultisetListRenderer extends DefaultListCellRenderer
{
  private static final long serialVersionUID = 1442128156943993715L;

    private Map myMap;
    private ImageIcon[] tileImages;
    private Image setImage = null;
    private double zoom = 1;

    public MultisetListRenderer() {
        setOpaque(true);
        try {
            setImage = MapEditor.loadImageResource("resources/source.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MultisetListRenderer(Map m){
        this();
        myMap = m;
        buildList();
    }

    public MultisetListRenderer(Map m, double zoom) {
        this();
        myMap = m;
        this.zoom = zoom;
        buildList();
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        if (value != null && index >= 0) {
            Tile tile = null;

            if (value instanceof Tile) {
                tile = (Tile)value;
                if (tile != null) {
                    setIcon(tileImages[index]);
                    setText("Tile " + tile.getId());
                } else {
                    setIcon(null);
                    setText("No tile");
                }
            } else if (value instanceof TileSet) {
                TileSet ts = (TileSet)value;
                if(ts != null) {
                    setIcon(new ImageIcon(setImage));
                    setText("Tileset " + ts.getName());
                } else {
                    setIcon(null);
                    setText("");
                }
            }

            // Scale image of selected tile up
            if (isSelected && tile != null) {
                setIcon(new ImageIcon(tile.getScaledImage(1.0)));
            }
        }
        return this;
    }


    private void buildList() {
        List<TileSet> sets = myMap.getTilesets();
        int curSlot = 0;
        Iterator<TileSet> itr = sets.iterator();
        int totalSlots = sets.size();

        itr = sets.iterator();
        while (itr.hasNext()) {
            TileSet ts = itr.next();
            totalSlots += ts.size();
        }
        tileImages = new ImageIcon[totalSlots];

        itr = sets.iterator();
        while (itr.hasNext()) {
            TileSet ts = itr.next();
            tileImages[curSlot++] = new ImageIcon(setImage);

            Iterator tileIterator = ts.iterator();

            while (tileIterator.hasNext()) {
                Tile tile = (Tile)tileIterator.next();
                Image img = tile.getScaledImage(zoom);
                if (img != null) {
                    tileImages[curSlot] = new ImageIcon(img);
                } else {
                    tileImages[curSlot] = null;
                }
                curSlot++;
            }
        }
    }
}
