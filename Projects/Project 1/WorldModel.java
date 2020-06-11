import processing.core.PImage;

import java.util.*;

public final class WorldModel
{
    private int numRows;
    private int numCols;
    private Background background[][];
    private Entity occupancy[][];
    private Set<Entity> entities;

    public static final int PROPERTY_KEY = 0;

    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;

    public static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;
    public static final int ORE_REACH = 1;

    public static final String BGND_KEY = "background";
    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;

    public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 4;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;

    public static final String SMITH_KEY = "blacksmith";
    public static final int SMITH_NUM_PROPERTIES = 4;
    public static final int SMITH_ID = 1;
    public static final int SMITH_COL = 2;
    public static final int SMITH_ROW = 3;

    public static final String VEIN_KEY = "vein";
    public static final int VEIN_NUM_PROPERTIES = 5;
    public static final int VEIN_ID = 1;
    public static final int VEIN_COL = 2;
    public static final int VEIN_ROW = 3;
    public static final int VEIN_ACTION_PERIOD = 4;

    public int getNumRows(){return numRows; }
    public int getNumCols() { return numCols; }
    public Set<Entity> getEntities() { return entities; }

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }

    public Optional<PImage> getBackgroundImage(Point pos)
    {
        if (this.withinBounds(pos)) {
            return Optional.of(ImageStore.getCurrentImage(getBackgroundCell(pos)));
        }
        else {
            return Optional.empty();
        }
    }

    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.getY()][pos.getX()];
    }

    public void setOccupancyCell(Point pos, Entity entity)
    {
        this.occupancy[pos.getY()][pos.getX()] = entity;
    }

    public Background getBackgroundCell(Point pos) {
        return this.background[pos.getY()][pos.getX()];
    }

    public void setBackground(Point pos, Background background)
    {
        if (this.withinBounds(pos)) {
            this.setBackgroundCell(pos, background);
        }
    }

    public void setBackgroundCell(Point pos, Background background)
    {
        this.background[pos.getY()][pos.getX()] = background;
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }

    public void addEntity(Entity entity) {
        if (this.withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPosition());
    }

    public void removeEntityAt(Point pos) {
        if (this.withinBounds(pos) && this.getOccupancyCell(pos) != null) {
            Entity entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.setOccupancyCell(pos, null);
        }
    }

    public void tryAddEntity(Entity entity) {
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity(entity);
    }

    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getX() < this.numRows && pos.getX() >= 0 && pos.getX() < this.numCols;
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && this.getOccupancyCell(pos) != null;
    }

    public boolean parseBackground(String[] properties, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            this.setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }
        return properties.length == BGND_NUM_PROPERTIES;
    }

    public boolean parseMiner(String[] properties, ImageStore imageStore)
    {
        if (properties.length == MINER_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            Entity entity = createMinerNotFull(properties[MINER_ID],
                    Integer.parseInt(properties[MINER_LIMIT]),
                    pt, Integer.parseInt(
                            properties[MINER_ACTION_PERIOD]), Integer.parseInt(
                            properties[MINER_ANIMATION_PERIOD]),
                    imageStore.getImageList(MINER_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == MINER_NUM_PROPERTIES;
    }

    public boolean parseObstacle(String[] properties, ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = createObstacle(properties[OBSTACLE_ID], pt,
                    imageStore.getImageList(OBSTACLE_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    public boolean parseOre(String[] properties, ImageStore imageStore)
    {
        if (properties.length == ORE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                    Integer.parseInt(properties[ORE_ROW]));
            Entity entity = createOre(properties[ORE_ID], pt, Integer.parseInt(
                    properties[ORE_ACTION_PERIOD]),
                    imageStore.getImageList(ORE_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == ORE_NUM_PROPERTIES;
    }

    public boolean parseSmith(String[] properties, ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = createBlacksmith(properties[SMITH_ID], pt,
                    imageStore.getImageList(SMITH_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == SMITH_NUM_PROPERTIES;
    }

    public boolean parseVein(String[] properties, ImageStore imageStore)
    {
        if (properties.length == VEIN_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                    Integer.parseInt(properties[VEIN_ROW]));
            Entity entity = createVein(properties[VEIN_ID], pt,
                    Integer.parseInt(
                            properties[VEIN_ACTION_PERIOD]),
                    imageStore.getImageList(VEIN_KEY));
            this.tryAddEntity( entity);
        }
        return properties.length == VEIN_NUM_PROPERTIES;
    }

    public  Optional<Entity> nearestEntity(List<Entity> entities, Point pos)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = nearest.getPosition().distanceSquared(pos);

            for (Entity other : entities) {
                int otherDistance = nearest.getPosition().distanceSquared(pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }
            return Optional.of(nearest);
        }
    }

    public Optional<Entity> findNearest(Point pos, EntityKind kind)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : this.entities) {
            if (entity.getKind() == kind) {
                ofType.add(entity);
            }
        }
        return nearestEntity(ofType, pos);
    }

    public Optional<Point> findOpenAround(Point pos) {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
                Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
                if (this.withinBounds(newPt) && !this.isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }
        return Optional.empty();
    }

    public void load(Scanner in, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!processLine(in.nextLine(), imageStore)) {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e) {
                System.err.println(
                        String.format("invalid entry on line %d", lineNumber));
            }
            catch (IllegalArgumentException e) {
                System.err.println(
                        String.format("issue on line %d: %s", lineNumber,
                                e.getMessage()));
            }
            lineNumber++;
        }
    }

    public boolean processLine(String line, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return this.parseBackground(properties, imageStore);
                case MINER_KEY:
                    return this.parseMiner(properties, imageStore);
                case OBSTACLE_KEY:
                    return this.parseObstacle(properties, imageStore);
                case ORE_KEY:
                    return this.parseOre(properties, imageStore);
                case SMITH_KEY:
                    return this.parseSmith(properties, imageStore);
                case VEIN_KEY:
                    return this.parseVein(properties, imageStore);
            }
        }
        return false;
    }

    public Entity createBlacksmith(String id, Point position, List<PImage> images)
    {
        return new Entity(EntityKind.BLACKSMITH, id, position, images, 0, 0, 0, 0);
    }

    public Entity createMinerFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Entity(EntityKind.MINER_FULL, id, position, images,
                resourceLimit, resourceLimit, actionPeriod,
                animationPeriod);
    }

    public Entity createMinerNotFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Entity(EntityKind.MINER_NOT_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public Entity createObstacle(String id, Point position, List<PImage> images)
    {
        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, 0);
    }

    public Entity createOre(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.ORE, id, position, images, 0, 0, actionPeriod, 0);
    }

    public Entity createOreBlob(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Entity(EntityKind.ORE_BLOB, id, position, images, 0, 0, actionPeriod, animationPeriod);
    }

    public Entity createQuake(Point position, List<PImage> images)
    {
        return new Entity(EntityKind.QUAKE, QUAKE_ID, position, images, 0, 0,
                QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public Entity createVein(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.VEIN, id, position, images, 0, 0, actionPeriod, 0);
    }

}
