package ic2.api;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public final class Ic2Recipes
{
  private static List TileEntityCompressor_recipes;
  private static List TileEntityExtractor_recipes;
  private static List TileEntityMacerator_recipes;
  private static List TileEntityRecycler_blacklist;
  private static List TileEntityMatter_amplifiers;

  public static void addCraftingRecipe(ItemStack itemstack, Object[] aobj)
  {
    try
    {
      Class.forName(getPackage() + ".common.AdvRecipe").getMethod("addAndRegister", 
        new Class[] { 
        ItemStack.class, Array.newInstance(Object.class, 0).getClass() })
        .invoke(null, 
        new Object[] { 
        itemstack, aobj });
    }
    catch (Exception exception)
    {
      throw new RuntimeException(exception);
    }
  }

  public static void addShapelessCraftingRecipe(ItemStack itemstack, Object[] aobj)
  {
    try
    {
      Class.forName(getPackage() + ".common.AdvShapelessRecipe").getMethod("addAndRegister", 
        new Class[] { 
        ItemStack.class, Array.newInstance(Object.class, 0).getClass() })
        .invoke(null, 
        new Object[] { 
        itemstack, aobj });
    }
    catch (Exception exception)
    {
      throw new RuntimeException(exception);
    }
  }

  public static List getCompressorRecipes()
  {
    if (TileEntityCompressor_recipes == null)
    {
      try
      {
        TileEntityCompressor_recipes = (List)Class.forName(getPackage() + ".common.TileEntityCompressor").getField("recipes").get(null);
      }
      catch (Exception exception)
      {
        throw new RuntimeException(exception);
      }
    }

    return TileEntityCompressor_recipes;
  }

  public static void addCompressorRecipe(ItemStack itemstack, ItemStack itemstack1)
  {
    getCompressorRecipes().add(new AbstractMap.SimpleEntry(itemstack, itemstack1));
  }

  public static ItemStack getCompressorOutputFor(ItemStack itemstack, boolean flag)
  {
    return getOutputFor(itemstack, flag, getCompressorRecipes());
  }

  public static List getExtractorRecipes()
  {
    if (TileEntityExtractor_recipes == null)
    {
      try
      {
        TileEntityExtractor_recipes = (List)Class.forName(getPackage() + ".common.TileEntityExtractor").getField("recipes").get(null);
      }
      catch (Exception exception)
      {
        throw new RuntimeException(exception);
      }
    }

    return TileEntityExtractor_recipes;
  }

  public static void addExtractorRecipe(ItemStack itemstack, ItemStack itemstack1)
  {
    getExtractorRecipes().add(new AbstractMap.SimpleEntry(itemstack, itemstack1));
  }

  public static ItemStack getExtractorOutputFor(ItemStack itemstack, boolean flag)
  {
    return getOutputFor(itemstack, flag, getExtractorRecipes());
  }

  public static List getMaceratorRecipes()
  {
    if (TileEntityMacerator_recipes == null)
    {
      try
      {
        TileEntityMacerator_recipes = (List)Class.forName(getPackage() + ".common.TileEntityMacerator").getField("recipes").get(null);
      }
      catch (Exception exception)
      {
        throw new RuntimeException(exception);
      }
    }

    return TileEntityMacerator_recipes;
  }

  public static void addMaceratorRecipe(ItemStack itemstack, ItemStack itemstack1)
  {
    getMaceratorRecipes().add(new AbstractMap.SimpleEntry(itemstack, itemstack1));
  }

  public static ItemStack getMaceratorOutputFor(ItemStack itemstack, boolean flag)
  {
    return getOutputFor(itemstack, flag, getMaceratorRecipes());
  }

  private static ItemStack getOutputFor(ItemStack itemstack, boolean flag, List list)
  {
    assert (itemstack != null);

    for (Iterator iterator = list.iterator(); iterator.hasNext(); )
    {
      Map.Entry entry = (Map.Entry)iterator.next();

      if ((((ItemStack)entry.getKey()).doMaterialsMatch(itemstack)) && (itemstack.count >= ((ItemStack)entry.getKey()).count))
      {
        if (flag)
        {
          itemstack.count -= ((ItemStack)entry.getKey()).count;
        }

        return ((ItemStack)entry.getValue()).cloneItemStack();
      }
    }

    return null;
  }

  public static List getRecyclerBlacklist()
  {
    if (TileEntityRecycler_blacklist == null)
    {
      try
      {
        TileEntityRecycler_blacklist = (List)Class.forName(getPackage() + ".common.TileEntityRecycler").getField("blacklist").get(null);
      }
      catch (Exception exception)
      {
        throw new RuntimeException(exception);
      }
    }

    return TileEntityRecycler_blacklist;
  }

  public static void addRecyclerBlacklistItem(ItemStack itemstack)
  {
    getRecyclerBlacklist().add(itemstack);
  }

  public static void addRecyclerBlacklistItem(Item item)
  {
    addRecyclerBlacklistItem(new ItemStack(item, 1, -1));
  }

  public static void addRecyclerBlacklistItem(Block block)
  {
    addRecyclerBlacklistItem(new ItemStack(block, 1, -1));
  }

  public static boolean isRecyclerInputBlacklisted(ItemStack itemstack)
  {
    for (Iterator iterator = getRecyclerBlacklist().iterator(); iterator.hasNext(); )
    {
      ItemStack itemstack1 = (ItemStack)iterator.next();

      if (itemstack.doMaterialsMatch(itemstack1))
      {
        return true;
      }
    }

    return false;
  }

  public static List getScrapboxDrops()
  {
    try
    {
      return (List)Class.forName(getPackage() + ".common.ItemScrapbox").getMethod("getDropList", new Class[0]).invoke(null, new Object[0]);
    }
    catch (Exception exception)
    {
      throw new RuntimeException(exception);
    }
  }

  public static void addScrapboxDrop(ItemStack itemstack, float f)
  {
    try
    {
      Class.forName(getPackage() + ".common.ItemScrapbox").getMethod("addDrop", 
        new Class[] { 
        ItemStack.class, Float.TYPE })
        .invoke(null, 
        new Object[] { 
        itemstack, Float.valueOf(f) });
    }
    catch (Exception exception)
    {
      throw new RuntimeException(exception);
    }
  }

  public static void addScrapboxDrop(Item item, float f)
  {
    addScrapboxDrop(new ItemStack(item, 1), f);
  }

  public static void addScrapboxDrop(Block block, float f)
  {
    addScrapboxDrop(new ItemStack(block), f);
  }

  public static List getMatterAmplifiers()
  {
    if (TileEntityMatter_amplifiers == null)
    {
      try
      {
        TileEntityMatter_amplifiers = (List)Class.forName(getPackage() + ".common.TileEntityMatter").getField("amplifiers").get(null);
      }
      catch (Exception exception)
      {
        throw new RuntimeException(exception);
      }
    }

    return TileEntityMatter_amplifiers;
  }

  public static void addMatterAmplifier(ItemStack itemstack, int i)
  {
    getMatterAmplifiers().add(new AbstractMap.SimpleEntry(itemstack, Integer.valueOf(i)));
  }

  public static void addMatterAmplifier(Item item, int i)
  {
    addMatterAmplifier(new ItemStack(item, 1, -1), i);
  }

  public static void addMatterAmplifier(Block block, int i)
  {
    addMatterAmplifier(new ItemStack(block, 1, -1), i);
  }

  private static String getPackage()
  {
    Package package1 = Ic2Recipes.class.getPackage();

    if (package1 != null)
    {
      return package1.getName().substring(0, package1.getName().lastIndexOf('.'));
    }

    return "ic2";
  }
}