# Profiler
A chunk generation profiler

![Build Mod](https://github.com/TerraForged/Profiler/workflows/Build%20Mod/badge.svg)

#### Downloads
Goto '[Actions](https://github.com/TerraForged/Profiler/actions)' \>\> Select the latest successful run \>\> Click the 'jars' link

https://github.com/TerraForged/Profiler/actions

#### About
This mod injects a series profiling timers into various levels of world-gen code in  
order to build up a (rough) idea of how long each part of chunk generation takes to  
execute.

This may or may not prove to be a useful tool for profiling world-gen and tracing  
poorly performing modded additions to generation.

Notes:
- Timing data is spewed periodically into the logs.
- Each data point is an average time per evaluation.
- You probably shouldn't run this mod all the time - it gets a bit spammy.

#### Sample Report
```
####################################################################################################################
                                                   TIMINGS REPORT
####################################################################################################################
- light                                                                       - Average: 0.011ms    - Samples: 7627
- structure_starts                                                            - Average: 0.306ms    - Samples: 8971
- structure_references                                                        - Average: 0.073ms    - Samples: 5888
- biomes                                                                      - Average: 0.085ms    - Samples: 5888
- noise                                                                       - Average: 3.617ms    - Samples: 5887
- surface                                                                     - Average: 0.627ms    - Samples: 5887
   surface.minecraft:swamp                                                      Average: 0.002ms      Samples: 92852
   surface.minecraft:default                                                    Average: 0.002ms      Samples: 96467
   surface.minecraft:mountain                                                   Average: 0.002ms      Samples: 17715
   surface.minecraft:frozen_ocean                                               Average: 0.024ms      Samples: 38
- carvers                                                                     - Average: 0.619ms    - Samples: 5887
   carvers.minecraft:cave                                                       Average: 0.011ms      Samples: 46460
   carvers.minecraft:canyon                                                     Average: 0.016ms      Samples: 31446
- liquid_carvers                                                              - Average: 0.026ms    - Samples: 5887
   liquid_carvers.minecraft:underwater_cave                                     Average: 0.009ms      Samples: 5677
   liquid_carvers.minecraft:underwater_canyon                                   Average: 0.017ms      Samples: 1429
- features                                                                    - Average: 2.860ms    - Samples: 5534
   features.minecraft:monster_room                                              Average: 0.003ms      Samples: 44272
   features.minecraft:ore                                                       Average: 0.014ms      Samples: 60680
   features.minecraft:disk                                                      Average: 0.003ms      Samples: 26318
   features.minecraft:tree                                                      Average: 0.077ms      Samples: 33068
   features.minecraft:flower                                                    Average: 0.012ms      Samples: 11540
   features.minecraft:random_patch                                              Average: 0.007ms      Samples: 92761
   features.minecraft:spring_feature                                            Average: 0.000ms      Samples: 87380
   features.minecraft:seagrass                                                  Average: 0.001ms      Samples: 44872
   features.minecraft:freeze_top_layer                                          Average: 0.164ms      Samples: 5534
   features.minecraft:lake                                                      Average: 0.687ms      Samples: 1946
   features.minecraft:fossil                                                    Average: 2.127ms      Samples: 6
   features.minecraft:mineshaft                                                 Average: 0.110ms      Samples: 2049
   features.minecraft:random_selector                                           Average: 0.072ms      Samples: 30435
   features.minecraft:simple_random_selector                                    Average: 0.015ms      Samples: 1433
   features.minecraft:simple_random_selector.minecraft:no_bonemeal_flower       Average: 0.012ms      Samples: 363
   features.minecraft:village                                                   Average: 1.104ms      Samples: 160
   features.minecraft:ruined_portal                                             Average: 4.965ms      Samples: 11
   features.minecraft:emerald_ore                                               Average: 0.001ms      Samples: 5669
   features.minecraft:simple_block                                              Average: 0.000ms      Samples: 8474
   features.minecraft:kelp                                                      Average: 0.003ms      Samples: 6682
   features.minecraft:ocean_ruin                                                Average: 7.485ms      Samples: 4
- spawn                                                                       - Average: 0.071ms    - Samples: 5182
- heightmaps                                                                  - Average: 0.001ms    - Samples: 5182
- full                                                                        - Average: 0.006ms    - Samples: 5182
                                            AVERAGE: 8.302ms PER CHUNK

####################################################################################################################
```
