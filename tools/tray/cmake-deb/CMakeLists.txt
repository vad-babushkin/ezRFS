cmake_minimum_required(VERSION 3.22)
project(ezRFS-Tray)

set(CPACK_PACKAGE_VERSION_MAJOR "251225")
set(CPACK_PACKAGE_VERSION_MINOR "0")
set(CPACK_PACKAGE_VERSION_PATCH "11")

# Define install paths
set(INSTALL_DIR /opt/ezRFS)

# Install the main JAR
install(FILES target/ezrfs-tray-jar-with-dependencies.jar DESTINATION ${INSTALL_DIR} COMPONENT Core)

# Install launcher scripts
install(FILES src/main/bash/ezrfs-tray DESTINATION ${INSTALL_DIR} COMPONENT Launcher)

#install(FILES LICENSE.txt README.md DESTINATION doc COMPONENT Docs)

# Packaging configuration
#set(CPACK_RESOURCE_FILE_LICENSE "${CMAKE_CURRENT_SOURCE_DIR}/LICENSE.txt")

set(CPACK_PACKAGE_NAME "ezRFS-Tray")
set(CPACK_PACKAGE_VERSION "${CPACK_PACKAGE_VERSION_MAJOR}.${CPACK_PACKAGE_VERSION_MINOR}.${CPACK_PACKAGE_VERSION_PATCH}")
set(CPACK_PACKAGE_DESCRIPTION_SUMMARY "ezRFS-Tray")
set(CPACK_PACKAGE_VENDOR "iconiux")
set(CPACK_PACKAGE_CONTACT "dev@iconiux.com")
# set(CPACK_GENERATOR "TGZ")  # Or use ZIP, DEB, RPM, NSIS, etc.

# Define components for packaging
set(CPACK_COMPONENTS_ALL Core Launcher)

# Optional: descriptions for each component
set(CPACK_COMPONENT_CORE_DESCRIPTION "Main JAR")
#set(CPACK_COMPONENT_DEPENDENCIES_DESCRIPTION "Required dependency libraries")
set(CPACK_COMPONENT_LAUNCHER_DESCRIPTION "Launcher script")
#set(CPACK_COMPONENT_DOCS_DESCRIPTION "License and README")

# Optional: mark some components as required
set(CPACK_COMPONENT_CORE_REQUIRED TRUE)

# Optional: group components in installer UI
set(CPACK_COMPONENT_GROUP_APP_GROUP "iconiux Components")
set(CPACK_COMPONENT_CORE_GROUP "APP_GROUP")
#set(CPACK_COMPONENT_DEPENDENCIES_GROUP "APP_GROUP")
set(CPACK_COMPONENT_LAUNCHER_GROUP "APP_GROUP")
#set(CPACK_COMPONENT_DOCS_GROUP "APP_GROUP")

# NSIS packaging setup
set(CPACK_GENERATOR "NSIS")
set(CPACK_NSIS_MODIFY_PATH ON)
set(CPACK_NSIS_DISPLAY_NAME "ezRFS-Tray Installer")
# set(CPACK_NSIS_INSTALLED_ICON_NAME "bin\\run.bat")
#set(CPACK_RESOURCE_FILE_LICENSE "${CMAKE_CURRENT_SOURCE_DIR}/LICENSE.txt")
#set(CPACK_RESOURCE_FILE_README "${CMAKE_CURRENT_SOURCE_DIR}/README.md")

# Use DEB for Debian package
set(CPACK_GENERATOR "DEB")
set(CPACK_DEBIAN_PACKAGE_MAINTAINER "iconiux")  # Required for DEB

include(CPack)



