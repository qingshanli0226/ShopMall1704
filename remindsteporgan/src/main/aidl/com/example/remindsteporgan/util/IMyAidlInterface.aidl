// IMyAidlInterface.aidl
package util;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
 /**
      * Demonstrates some basic types that you can use as parameters
      * and return values in AIDL.
      */
     String getAnotherProcessData(in String name);
}
