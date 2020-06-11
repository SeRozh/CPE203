import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

import processing.core.PImage;
import processing.core.PApplet;

public final class Functions
{
    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }
}
