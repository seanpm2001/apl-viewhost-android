# Distributed under the OSI-approved BSD 3-Clause License.  See accompanying
# file Copyright.txt or https://cmake.org/licensing for details.

cmake_minimum_required(VERSION 3.5)

if("/Volumes/workplace/APLViewhostAndroid/src/APLViewhostAndroid/discovery/../../APLCoreEngine/thirdparty/googletest-release-1.8.1.tar.gz" STREQUAL "")
  message(FATAL_ERROR "LOCAL can't be empty")
endif()

if(NOT EXISTS "/Volumes/workplace/APLViewhostAndroid/src/APLViewhostAndroid/discovery/../../APLCoreEngine/thirdparty/googletest-release-1.8.1.tar.gz")
  message(FATAL_ERROR "File not found: /Volumes/workplace/APLViewhostAndroid/src/APLViewhostAndroid/discovery/../../APLCoreEngine/thirdparty/googletest-release-1.8.1.tar.gz")
endif()

if("MD5" STREQUAL "")
  message(WARNING "File will not be verified since no URL_HASH specified")
  return()
endif()

if("2e6fbeb6a91310a16efe181886c59596" STREQUAL "")
  message(FATAL_ERROR "EXPECT_VALUE can't be empty")
endif()

message(STATUS "verifying file...
     file='/Volumes/workplace/APLViewhostAndroid/src/APLViewhostAndroid/discovery/../../APLCoreEngine/thirdparty/googletest-release-1.8.1.tar.gz'")

file("MD5" "/Volumes/workplace/APLViewhostAndroid/src/APLViewhostAndroid/discovery/../../APLCoreEngine/thirdparty/googletest-release-1.8.1.tar.gz" actual_value)

if(NOT "${actual_value}" STREQUAL "2e6fbeb6a91310a16efe181886c59596")
  message(FATAL_ERROR "error: MD5 hash of
  /Volumes/workplace/APLViewhostAndroid/src/APLViewhostAndroid/discovery/../../APLCoreEngine/thirdparty/googletest-release-1.8.1.tar.gz
does not match expected value
  expected: '2e6fbeb6a91310a16efe181886c59596'
    actual: '${actual_value}'
")
endif()

message(STATUS "verifying file... done")
