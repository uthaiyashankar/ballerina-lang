/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.ballerinalang.net.ws.nativeimpl;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeEnum;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.AbstractNativeFunction;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.net.ws.Constants;
import org.ballerinalang.net.ws.WebSocketConnectionManager;
import org.ballerinalang.util.exceptions.BallerinaException;

/**
 * Get parent connection is exists.
 *
 * @since 0.94
 */

@BallerinaFunction(
        packageName = "ballerina.net.ws",
        functionName = "getParentConnection",
        args = {@Argument(name = "conn", type = TypeEnum.STRUCT, structType = "Connection",
                          structPackage = "ballerina.net.ws")},
        returnType = {@ReturnType(type = TypeEnum.STRUCT, structType = "Connection",
                                  structPackage = "ballerina.net.ws")},
        isPublic = true
)
public class GetParentConnection extends AbstractNativeFunction {

    @Override
    public BValue[] execute(Context context) {

        if (context.getServiceInfo() == null ||
                !context.getServiceInfo().getProtocolPkgPath().equals(Constants.WEBSOCKET_PACKAGE_NAME)) {
            throw new BallerinaException("This function is only working with WebSocket services");
        }

        BStruct wsConnection = (BStruct) getRefArgument(context, 0);
        String parentConnectionID = (String) wsConnection.getNativeData(Constants.NATIVE_DATA_PARENT_CONNECTION_ID);
        BStruct parentConnection = WebSocketConnectionManager.getInstance().getConnection(parentConnectionID);
        return getBValues(parentConnection);
    }
}
