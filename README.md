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
- STRUCTURE_STARTS                                                  - Average: 0.244ms    - Samples: 7604
- STRUCTURE_REFERENCES                                              - Average: 0.083ms    - Samples: 3836
- BIOMES                                                            - Average: 0.094ms    - Samples: 3836
- NOISE                                                             - Average: 3.731ms    - Samples: 3836
- SURFACE                                                           - Average: 0.608ms    - Samples: 3836
   surface/default                                                    Average: 0.001ms      Samples: 55812
   surface/swamp                                                      Average: 0.001ms      Samples: 38576
   surface/mountain                                                   Average: 0.002ms      Samples: 87396
   surface/gravelly_mountain                                          Average: 0.002ms      Samples: 232
- CARVERS                                                           - Average: 0.553ms    - Samples: 3836
   carvers/cave                                                       Average: 0.012ms      Samples: 51066
   carvers/canyon                                                     Average: 0.015ms      Samples: 19130
- LIQUID_CARVERS                                                    - Average: 0.047ms    - Samples: 3835
   liquid_carvers/underwater_cave                                     Average: 0.005ms      Samples: 13132
   liquid_carvers/underwater_canyon                                   Average: 0.018ms      Samples: 3602
- FEATURES                                                          - Average: 2.826ms    - Samples: 3385
   features/decorated                                                 Average: 0.007ms      Samples: 47413
   features/decorated/monster_room                                    Average: 0.003ms      Samples: 27080
   features/decorated/ore                                             Average: 0.011ms      Samples: 41377
   features/decorated/disk                                            Average: 0.008ms      Samples: 14865
   features/decorated/random_selector                                 Average: 0.087ms      Samples: 13053
   features/decorated/random_selector/tree                            Average: 0.090ms      Samples: 16140
   features/decorated/random_selector/huge_brown_mushroom             Average: 0.033ms      Samples: 121
   features/decorated/random_selector/huge_red_mushroom               Average: 0.069ms      Samples: 235
   features/decorated/flower                                          Average: 0.012ms      Samples: 7090
   features/decorated/random_patch                                    Average: 0.006ms      Samples: 58040
   features/decorated/spring_feature                                  Average: 0.000ms      Samples: 36950
   features/decorated/simple_random_selector                          Average: 0.023ms      Samples: 1538
   features/decorated/simple_random_selector/no_bonemeal_flower       Average: 0.012ms      Samples: 312
   features/decorated/simple_random_selector/coral_tree               Average: 0.043ms      Samples: 80
   features/decorated/simple_random_selector/coral_mushroom           Average: 0.099ms      Samples: 99
   features/decorated/simple_random_selector/coral_claw               Average: 0.041ms      Samples: 86
   features/decorated/lake                                            Average: 0.803ms      Samples: 1149
   features/decorated/seagrass                                        Average: 0.001ms      Samples: 87648
   features/decorated/fossil                                          Average: 1.644ms      Samples: 15
   features/decorated/emerald_ore                                     Average: 0.001ms      Samples: 2286
   features/decorated/kelp                                            Average: 0.003ms      Samples: 8453
   features/decorated/simple_block                                    Average: 0.000ms      Samples: 8176
   features/decorated/sea_pickle                                      Average: 0.122ms      Samples: 2
   features/freeze_top_layer                                          Average: 0.134ms      Samples: 3385
   features/mineshaft                                                 Average: 0.110ms      Samples: 1211
   features/ruined_portal                                             Average: 4.679ms      Samples: 2
   features/village                                                   Average: 0.863ms      Samples: 127
   features/village/block_pile                                        Average: 0.095ms      Samples: 2
   features/shipwreck                                                 Average: 0.680ms      Samples: 14
   features/ocean_ruin                                               Average: 10.885ms      Samples: 2
- LIGHT                                                             - Average: 0.010ms    - Samples: 2942
- SPAWN                                                             - Average: 0.086ms    - Samples: 2939
- HEIGHTMAPS                                                        - Average: 0.001ms    - Samples: 2939
- FULL                                                              - Average: 0.007ms    - Samples: 2939
                                       AVERAGE: 8.291ms PER CHUNK

##########################################################################################################
```
