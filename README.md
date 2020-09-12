# Profiler
A chunk generation profiler

![Build Mod](https://github.com/TerraForged/Profiler/workflows/Build%20Mod/badge.svg)

#### Downloads
Goto '[Actions](https://github.com/TerraForged/Profiler/actions)' \>\> Select the latest successful run \>\> Click the 'jars' link

https://github.com/TerraForged/Profiler/actions

#### About
This mod injects a series of profiling timers into various levels of world-gen code in  
order to build up a (rough) idea of how long each part of chunk generation takes to  
execute.

This may or may not prove to be a useful tool for profiling world-gen and identifying  
poorly performing additions to world generation.

Notes:
- Timing data is spewed periodically into the logs.
- Each data point is an average time per evaluation.
- You probably shouldn't run this mod all the time - it gets a bit spammy.

#### Sample Report
```
##########################################################################################################
                                              TIMINGS REPORT
##########################################################################################################
- STRUCTURE_STARTS                                                  - Average: 0.959ms    - Samples: 1916
- STRUCTURE_REFERENCES                                              - Average: 0.100ms    - Samples: 1124
- BIOMES                                                            - Average: 0.261ms    - Samples: 1124
- NOISE                                                             - Average: 3.948ms    - Samples: 1124
- SURFACE                                                           - Average: 0.667ms    - Samples: 1121
   surface/default                                                    Average: 0.001ms      Samples: 86976
- CARVERS                                                           - Average: 0.364ms    - Samples: 1109
   carvers/cave                                                       Average: 0.011ms      Samples: 27669
   carvers/canyon                                                     Average: 0.010ms      Samples: 5939
- LIQUID_CARVERS                                                    - Average: 0.295ms    - Samples: 1109
   liquid_carvers/underwater_canyon                                   Average: 0.019ms      Samples: 6671
   liquid_carvers/underwater_cave                                     Average: 0.008ms      Samples: 17617
- FEATURES                                                          - Average: 3.341ms    - Samples: 998
   features/decorated                                                 Average: 0.008ms      Samples: 33823
   features/decorated/lake                                            Average: 0.793ms      Samples: 299
   features/decorated/monster_room                                    Average: 0.002ms      Samples: 7984
   features/decorated/ore                                             Average: 0.014ms      Samples: 99800
   features/decorated/disk                                            Average: 0.011ms      Samples: 4990
   features/decorated/flower                                          Average: 0.018ms      Samples: 2004
   features/decorated/random_patch                                    Average: 0.007ms      Samples: 12719
   features/decorated/spring_feature                                  Average: 0.000ms      Samples: 69860
   features/decorated/seagrass                                        Average: 0.001ms      Samples: 57296
   features/decorated/simple_block                                    Average: 0.000ms      Samples: 36526
   features/decorated/kelp                                            Average: 0.004ms      Samples: 15720
   features/decorated/random_selector                                 Average: 2.443ms      Samples: 246
   features/decorated/random_selector/tree                            Average: 2.439ms      Samples: 246
   features/decorated/simple_random_selector                          Average: 0.059ms      Samples: 211
   features/decorated/simple_random_selector/no_bonemeal_flower       Average: 0.025ms      Samples: 7
   features/decorated/simple_random_selector/coral_claw               Average: 0.041ms      Samples: 70
   features/decorated/simple_random_selector/coral_tree               Average: 0.037ms      Samples: 62
   features/decorated/simple_random_selector/coral_mushroom           Average: 0.104ms      Samples: 58
   features/decorated/sea_pickle                                      Average: 0.100ms      Samples: 4
   features/freeze_top_layer                                          Average: 0.122ms      Samples: 998
   features/mineshaft                                                 Average: 0.105ms      Samples: 394
   features/ocean_ruin                                                Average: 4.198ms      Samples: 12
   features/shipwreck                                                 Average: 1.303ms      Samples: 10
   features/ruined_portal                                             Average: 8.022ms      Samples: 1
- LIGHT                                                             - Average: 0.010ms    - Samples: 2221
- SPAWN                                                             - Average: 0.165ms    - Samples: 889
- HEIGHTMAPS                                                        - Average: 0.002ms    - Samples: 889
- FULL                                                              - Average: 0.006ms    - Samples: 889
                                       AVERAGE: 10.118ms PER CHUNK

##########################################################################################################
```
