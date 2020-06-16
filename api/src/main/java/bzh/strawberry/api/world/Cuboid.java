package bzh.strawberry.api.world;

import org.bukkit.Location;
import org.bukkit.World;

/*
This file Cuboid is part of a project StrawAPI.
It was created on 16/06/2020 at 00:42 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class Cuboid {

    private Location center;
    private Location pos1;
    private Location pos2;

    private World world;
    private int xMin, yMin, zMin, xMax, yMax, zMax;


    public Cuboid(Location p1, Location p2){
        this.pos1 = p1;
        this.pos2 = p2;

        try{
            this.center = computeCenter(pos1, pos2);
        }
        catch (Exception e){
            this.center = null;
        }

        this.world = p1.getWorld();
        this.xMin = Math.min(p1.getBlockX(), p2.getBlockX());
        this.xMax = Math.max(p1.getBlockX(), p2.getBlockX());
        this.yMin = Math.min(p1.getBlockY(), p2.getBlockY());
        this.yMax = Math.max(p1.getBlockY(), p2.getBlockY());
        this.zMin = Math.min(p1.getBlockZ(), p2.getBlockZ());
        this.zMax = Math.max(p1.getBlockZ(), p2.getBlockZ());

    }

    public Cuboid(Location p1, Location p2, Location center){
        this.pos1 = p1;
        this.pos2 = p2;
        this.center = center;
    }

    public Location computeCenter(Location p1, Location p2) throws Exception {

        if(p1.getWorld() != p2.getWorld()) {
            throw new Exception("The Locations aren't in the same world.");
        }
        return new Location(p1.getWorld(), (p2.getX() + p1.getX())/2, (p2.getY() + p1.getY())/2, (p2.getZ() + p1.getZ())/2, (p2.getPitch() + p1.getPitch())/2, (p2.getYaw() + p1.getYaw())/2);
    }

    public Location getCenter(){
        return this.center;
    }

    public Location getPos1() {
        return this.pos1;
    }

    public Location getPos2() {
        return this.pos2;
    }

    public boolean isIn(Location loc) {
        return loc.getWorld() == this.world && loc.getBlockX() >= this.xMin && loc.getBlockX() <= this.xMax && loc.getBlockY() >= this.yMin && loc.getBlockY() <= this.yMax && loc.getBlockZ() >= this.zMin && loc.getBlockZ() <= this.zMax;
    }

    public boolean isInWithMarge(Location loc, double marge) {
        return loc.getWorld() == this.world && loc.getX() >= this.xMin - marge && loc.getX() <= this.xMax + marge && loc.getY() >= this.yMin- marge && loc.getY() <= this.yMax + marge && loc.getZ() >= this.zMin - marge && loc.getZ() <= this.zMax + marge;
    }

    public static Cuboid fromCenter(Location center, int radius) {
        Location post1 = new Location(center.getWorld(), center.getX() + radius/2, 0.0D, center.getZ() + radius/2);
        Location post2 = new Location(center.getWorld(), center.getX() - radius/2, 255.0D, center.getZ() - radius/2);
        return new Cuboid(post1, post2);
    }

}
