cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -UNDEBUG")

add_library(native-lib SHARED src/main/cpp/native-lib.cpp src/main/cpp/Max.cpp)

# Include libraries needed for hello-jni lib

target_link_libraries(native-lib)