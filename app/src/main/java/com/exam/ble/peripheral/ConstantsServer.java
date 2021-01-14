/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exam.ble.peripheral;


public interface ConstantsServer {

    // Message types sent from the BluetoothChatService Handler
    public static final int RUN = 0X01;
    public static final int STOP = 0X02;
    public static final int VERSION = 0X03;
    public static final int MESURE = 0X04;
    public static final String retStr[] = {"RUNNING","STOPPING","NONE","MESURE"};

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

}
