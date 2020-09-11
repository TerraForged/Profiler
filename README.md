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

This may or may not prove to be a useful tool for profiling world-gen and identifying  
poorly performing modded additions to generation.

Notes:
- Timing data is spewed periodically into the logs.
- Each data point is an average time per evaluation.
- You probably shouldn't run this mod all the time - it gets a bit spammy.

#### Sample Report
```
##########################################################################################################
                                              TIMINGS REPORT
##########################################################################################################
- LIGHT                                                             - Average: 0.011ms    - Samples: 2792
- STRUCTURE_STARTS                                                  - Average: 0.443ms    - Samples: 4365
- STRUCTURE_REFERENCES                                              - Average: 0.106ms    - Samples: 2028
- BIOMES                                                            - Average: 0.203ms    - Samples: 2028
- NOISE                                                             - Average: 3.657ms    - Samples: 2028
- SURFACE                                                           - Average: 0.611ms    - Samples: 2028
   surface/default                                                    Average: 0.001ms      Samples: 89203
   surface/badlands                                                   Average: 0.002ms      Samples: 29890
   surface/wooded_badlands                                            Average: 0.010ms      Samples: 75
- CARVERS                                                           - Average: 0.282ms    - Samples: 2028
   carvers/canyon                                                     Average: 0.007ms      Samples: 11614
   carvers/cave                                                       Average: 0.010ms      Samples: 41220
- LIQUID_CARVERS                                                    - Average: 0.236ms    - Samples: 2028
   liquid_carvers/underwater_cave                                     Average: 0.007ms      Samples: 39331
   liquid_carvers/underwater_canyon                                   Average: 0.015ms      Samples: 9163
- FEATURES                                                          - Average: 2.431ms    - Samples: 1758
   features/decorated                                                 Average: 0.009ms      Samples: 67730
   features/decorated/monster_room                                    Average: 0.002ms      Samples: 14064
   features/decorated/ore                                             Average: 0.011ms      Samples: 77860
   features/decorated/disk                                            Average: 0.009ms      Samples: 8790
   features/decorated/flower                                          Average: 0.014ms      Samples: 3320
   features/decorated/random_patch                                    Average: 0.006ms      Samples: 23026
   features/decorated/spring_feature                                  Average: 0.000ms      Samples: 23060
   features/decorated/seagrass                                        Average: 0.001ms      Samples: 8736
   features/decorated/kelp                                            Average: 0.003ms      Samples: 27330
   features/decorated/lake                                            Average: 0.460ms      Samples: 555
   features/decorated/simple_block                                    Average: 0.000ms      Samples: 49038
   features/decorated/random_selector                                 Average: 0.114ms      Samples: 296
   features/decorated/random_selector/tree                            Average: 0.110ms      Samples: 296
   features/decorated/simple_random_selector                          Average: 0.047ms      Samples: 466
   features/decorated/simple_random_selector/no_bonemeal_flower       Average: 0.045ms      Samples: 5
   features/decorated/simple_random_selector/coral_tree               Average: 0.031ms      Samples: 148
   features/decorated/simple_random_selector/coral_claw               Average: 0.032ms      Samples: 150
   features/decorated/simple_random_selector/coral_mushroom           Average: 0.074ms      Samples: 152
   features/decorated/sea_pickle                                      Average: 0.094ms      Samples: 4
   features/mineshaft                                                 Average: 0.076ms      Samples: 829
   features/freeze_top_layer                                          Average: 0.110ms      Samples: 1758
   features/ocean_ruin                                                Average: 1.338ms      Samples: 21
   features/monument                                                  Average: 6.570ms      Samples: 16
   features/shipwreck                                                 Average: 1.857ms      Samples: 6
- SPAWN                                                             - Average: 0.105ms    - Samples: 1496
- HEIGHTMAPS                                                        - Average: 0.002ms    - Samples: 1496
- FULL                                                              - Average: 0.006ms    - Samples: 1496
                                       AVERAGE: 8.093ms PER CHUNK

##########################################################################################################
```
