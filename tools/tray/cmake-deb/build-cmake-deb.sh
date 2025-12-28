
mkdir -p build
cd build
cmake ../
cmake --build .
cpack -G DEB --config CPackConfig.cmake
cd ..