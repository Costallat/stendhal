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

package tiled.mapeditor.brush;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ListIterator;

import tiled.core.MapLayer;
import tiled.core.MultilayerPlane;
import tiled.core.TileLayer;

public class CustomBrush extends AbstractBrush
{
    public CustomBrush() {
        super();
    }

    public CustomBrush(MultilayerPlane m) {
        this();
        this.addAllLayers(m.getLayerList());
    }

    public Rectangle getBounds() {
        return getBounds();
    }

    /**
     * The custom brush will merge its internal layers onto the layers of the 
     * specified MultilayerPlane.
     *
     * @see TileLayer#mergeOnto(MapLayer)
     * @see Brush#commitPaint(MultilayerPlane, int, int, int)
     * @param mp         The MultilayerPlane to be affected
     * @param x          The x-coordinate where the user initiated the paint
     * @param y          The y-coordinate where the user initiated the paint
     * @param initLayer  The first layer to paint to.
     * @return The rectangular region affected by the painting  
     */
    public Rectangle commitPaint(MultilayerPlane mp, int x, int y,
            int initLayer)
    {
        Rectangle bounds = this.getBounds();
        int centerx = (int)(x - (bounds.width / 2));
        int centery = (int)(y - (bounds.height / 2));

        ListIterator itr = iterator();
        while (itr.hasNext()) {
            TileLayer tl = (TileLayer)itr.next();
            TileLayer tm = (TileLayer)mp.getLayer(initLayer++);
            if (tm != null && tm.isVisible()) {
                tl.setOffset(centerx, centery);
                tl.mergeOnto(tm);
            }
        }

        return new Rectangle(centerx, centery, bounds.width, bounds.height);
    }

    /* (non-Javadoc)
     * @see tiled.mapeditor.brush.Brush#paint(java.awt.Graphics, int, int)
     */
    public void paint(Graphics g, int x, int y) {
        // TODO Auto-generated method stub
    }

    public boolean equals(Brush b) {
        if (b instanceof CustomBrush) {
            //TODO: THIS
        }
        return false;
    }

    /** returns the affected layers */
    public MapLayer[] getAffectedLayers()
    {
      return new MapLayer[0];
    }
    
    public String getName()
    {
      return "Custom Brush";
    }
}
