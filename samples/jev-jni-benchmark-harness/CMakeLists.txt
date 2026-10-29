cmake_minimum_required(VERSION 3.20)
project(jev_jni_benchmark_harness LANGUAGES CXX)

find_package(JNI REQUIRED)

add_library(jevbench SHARED native/native_bench_harness.cpp)
target_compile_features(jevbench PRIVATE cxx_std_17)
target_include_directories(jevbench PRIVATE ${JNI_INCLUDE_DIRS})
if(JNI_LIBRARIES)
    target_link_libraries(jevbench PRIVATE ${JNI_LIBRARIES})
endif()

if(CMAKE_CXX_COMPILER_ID MATCHES "GNU|Clang")
    target_compile_options(jevbench PRIVATE -Wall -Wextra -Wpedantic)
endif()

set_target_properties(jevbench PROPERTIES
    CXX_VISIBILITY_PRESET hidden
    VISIBILITY_INLINES_HIDDEN YES
)
