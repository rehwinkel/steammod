package yellowstone.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

public class YellowstoneLakeFeature extends Feature<YellowstoneLakeConfig> {

    private static final BlockState AIR = Blocks.CAVE_AIR.getDefaultState();
    private static final BlockState WATER = Blocks.WATER.getDefaultState();
    private static final BlockState ORANGE_CONCRETE = Blocks.ORANGE_CONCRETE.getDefaultState();
    private static final BlockState YELLOW_CONCRETE = Blocks.YELLOW_CONCRETE.getDefaultState();
    private static final BlockState CYAN_CONCRETE = Blocks.CYAN_CONCRETE.getDefaultState();
    private static final BlockState BLUE_CONCRETE = Blocks.BLUE_CONCRETE.getDefaultState();

    public YellowstoneLakeFeature(Codec codec) {
        super(codec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader world, StructureManager structureManager, ChunkGenerator generator, Random rand, BlockPos pos, YellowstoneLakeConfig config) {
        while (pos.getY() > 5 && world.isAirBlock(pos)) {
            pos = pos.down();
        }

        if (pos.getY() <= 4) {
            return false;
        } else {
            pos = pos.down(4);
            if (structureManager.func_235011_a_(SectionPos.from(pos), Structure.field_236381_q_).findAny().isPresent()) {
                return false;
            } else {
                boolean[] aboolean = new boolean[2048];
                int i = rand.nextInt(4) + 4;

                for (int j = 0; j < i; ++j) {
                    double d0 = rand.nextDouble() * 6.0D + 3.0D;
                    double d1 = rand.nextDouble() * 4.0D + 2.0D;
                    double d2 = rand.nextDouble() * 6.0D + 3.0D;
                    double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                    double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                    double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                    for (int l = 1; l < 15; ++l) {
                        for (int i1 = 1; i1 < 15; ++i1) {
                            for (int j1 = 1; j1 < 7; ++j1) {
                                double d6 = ((double) l - d3) / (d0 / 2.0D);
                                double d7 = ((double) j1 - d4) / (d1 / 2.0D);
                                double d8 = ((double) i1 - d5) / (d2 / 2.0D);
                                double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                                if (d9 < 1.0D) {
                                    aboolean[(l * 16 + i1) * 8 + j1] = true;
                                }
                            }
                        }
                    }
                }

                for (int k1 = 0; k1 < 16; ++k1) {
                    for (int l2 = 0; l2 < 16; ++l2) {
                        for (int k = 0; k < 8; ++k) {
                            boolean flag = !aboolean[(k1 * 16 + l2) * 8 + k] && (k1 < 15 && aboolean[((k1 + 1) * 16 + l2) * 8 + k] || k1 > 0 && aboolean[((k1 - 1) * 16 + l2) * 8 + k] || l2 < 15 && aboolean[(k1 * 16 + l2 + 1) * 8 + k] || l2 > 0 && aboolean[(k1 * 16 + (l2 - 1)) * 8 + k] || k < 7 && aboolean[(k1 * 16 + l2) * 8 + k + 1] || k > 0 && aboolean[(k1 * 16 + l2) * 8 + (k - 1)]);
                            if (flag) {
                                Material material = world.getBlockState(pos.add(k1, k, l2)).getMaterial();
                                if (k >= 4 && material.isLiquid()) {
                                    return false;
                                }

                                if (k < 4 && !material.isSolid() && world.getBlockState(pos.add(k1, k, l2)) != WATER) {
                                    return false;
                                }
                            }
                        }
                    }
                }

                for (int l1 = 0; l1 < 16; ++l1) {
                    for (int i3 = 0; i3 < 16; ++i3) {
                        for (int i4 = 0; i4 < 8; ++i4) {
                            int i1 = (l1 * 16 + i3) * 8 + i4;
                            if (aboolean[i1]) {
                                world.setBlockState(pos.add(l1, i4, i3), i4 >= 4 ? AIR : WATER, 2);
                                boolean b = !aboolean[((l1 + 1) * 16 + i3) * 8 + i4];
                                boolean b1 = !aboolean[((l1 - 1) * 16 + i3) * 8 + i4];
                                boolean b2 = !aboolean[(l1 * 16 + (i3 + 1)) * 8 + i4];
                                boolean b3 = !aboolean[(l1 * 16 + (i3 - 1)) * 8 + i4];
                                boolean b4 = !aboolean[i1 - 1];
                                if (i4 == 3) {
                                    if (b) {
                                        world.setBlockState(pos.add((l1 + 1), i4, i3), ORANGE_CONCRETE, 2);
                                    }
                                    if (b1) {
                                        world.setBlockState(pos.add((l1 - 1), i4, i3), ORANGE_CONCRETE, 2);
                                    }
                                    if (b2) {
                                        world.setBlockState(pos.add(l1, i4, i3 + 1), ORANGE_CONCRETE, 2);
                                    }
                                    if (b3) {
                                        world.setBlockState(pos.add(l1, i4, i3 - 1), ORANGE_CONCRETE, 2);
                                    }
                                    if (b4) {
                                        world.setBlockState(pos.add(l1, i4 - 1, i3), YELLOW_CONCRETE, 2);
                                    }
                                }
                                if (i4 == 2) {
                                    if (b) {
                                        world.setBlockState(pos.add((l1 + 1), i4, i3), YELLOW_CONCRETE, 2);
                                    }
                                    if (b1) {
                                        world.setBlockState(pos.add((l1 - 1), i4, i3), YELLOW_CONCRETE, 2);
                                    }
                                    if (b2) {
                                        world.setBlockState(pos.add(l1, i4, i3 + 1), YELLOW_CONCRETE, 2);
                                    }
                                    if (b3) {
                                        world.setBlockState(pos.add(l1, i4, i3 - 1), YELLOW_CONCRETE, 2);
                                    }
                                    if (b4) {
                                        world.setBlockState(pos.add(l1, i4 - 1, i3), CYAN_CONCRETE, 2);
                                    }
                                }
                                if (i4 == 1) {
                                    if (b) {
                                        world.setBlockState(pos.add((l1 + 1), i4, i3), CYAN_CONCRETE, 2);
                                    }
                                    if (b1) {
                                        world.setBlockState(pos.add((l1 - 1), i4, i3), CYAN_CONCRETE, 2);
                                    }
                                    if (b2) {
                                        world.setBlockState(pos.add(l1, i4, i3 + 1), CYAN_CONCRETE, 2);
                                    }
                                    if (b3) {
                                        world.setBlockState(pos.add(l1, i4, i3 - 1), CYAN_CONCRETE, 2);
                                    }
                                    if (b4) {
                                        world.setBlockState(pos.add(l1, i4 - 1, i3), BLUE_CONCRETE, 2);
                                    }
                                }
                                if (i4 == 0) {
                                    if (b) {
                                        world.setBlockState(pos.add((l1 + 1), i4, i3), BLUE_CONCRETE, 2);
                                    }
                                    if (b1) {
                                        world.setBlockState(pos.add((l1 - 1), i4, i3), BLUE_CONCRETE, 2);
                                    }
                                    if (b2) {
                                        world.setBlockState(pos.add(l1, i4, i3 + 1), BLUE_CONCRETE, 2);
                                    }
                                    if (b3) {
                                        world.setBlockState(pos.add(l1, i4, i3 - 1), BLUE_CONCRETE, 2);
                                    }
                                    if (b4) {
                                        world.setBlockState(pos.add(l1, i4 - 1, i3), BLUE_CONCRETE, 2);
                                    }
                                }
                            }
                        }
                    }
                }

                for (int i2 = 0; i2 < 16; ++i2) {
                    for (int j3 = 0; j3 < 16; ++j3) {
                        for (int j4 = 4; j4 < 8; ++j4) {
                            if (aboolean[(i2 * 16 + j3) * 8 + j4]) {
                                BlockPos blockpos = pos.add(i2, j4 - 1, j3);
                                if (isDirt(world.getBlockState(blockpos).getBlock()) && world.getLightFor(LightType.SKY, pos.add(i2, j4, j3)) > 0) {
                                    Biome biome = world.getBiome(blockpos);
                                    if (biome.getSurfaceBuilderConfig().getTop().isIn(Blocks.MYCELIUM)) {
                                        world.setBlockState(blockpos, Blocks.MYCELIUM.getDefaultState(), 2);
                                    } else {
                                        world.setBlockState(blockpos, Blocks.GRASS_BLOCK.getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                    }
                }

                for (int k2 = 0; k2 < 16; ++k2) {
                    for (int l3 = 0; l3 < 16; ++l3) {
                        int l4 = 4;
                        BlockPos blockpos1 = pos.add(k2, 4, l3);
                        if (world.getBiome(blockpos1).doesWaterFreeze(world, blockpos1, false)) {
                            world.setBlockState(blockpos1, Blocks.ICE.getDefaultState(), 2);
                        }
                    }
                }

                return true;
            }
        }
    }
}
