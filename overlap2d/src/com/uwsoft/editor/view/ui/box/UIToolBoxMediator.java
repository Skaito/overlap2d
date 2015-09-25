/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.uwsoft.editor.view.ui.box;

import com.badlogic.gdx.utils.Array;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.uwsoft.editor.Overlap2DFacade;
import com.uwsoft.editor.view.stage.tools.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.apache.commons.lang3.ArrayUtils.toArray;

/**
 * Created by sargis on 4/9/15.
 */
public class UIToolBoxMediator extends SimpleMediator<UIToolBox> {
    private static final String TAG = UIToolBoxMediator.class.getCanonicalName();
    public static final String NAME = TAG;

    private static final String PREFIX =  "com.uwsoft.editor.view.ui.box.UIToolBoxMediator.";
    public static final String TOOL_SELECTED = PREFIX + ".TOOL_CHANGED";


    private String currentTool;
    private Array<String> toolList;


    public UIToolBoxMediator() {
        super(NAME, new UIToolBox());
    }

    @Override
    public void onRegister() {
        facade = Overlap2DFacade.getInstance();

        toolList = getToolNameList();
        currentTool = SelectionTool.NAME;

        viewComponent.createToolButtons(toolList, new ArrayList(getToolNameMap().values()));
    }

	public Array<String> getToolNameList() {
		return Array.with(getToolNameMap().keySet().toArray(new String[0]));
	}
	
    private Map<String, String> getToolNameMap() {
        Map<String, String> toolNames = new LinkedHashMap<>();
        toolNames.put(SelectionTool.NAME, SelectionTool.TITLE);
        toolNames.put(TransformTool.NAME, TransformTool.TITLE);
        toolNames.put(TextTool.NAME, TextTool.TITLE);
        toolNames.put(PointLightTool.NAME, PointLightTool.TITLE);
        toolNames.put(ConeLightTool.NAME, ConeLightTool.TITLE);
        toolNames.put(PolygonTool.NAME, PolygonTool.TITLE);
        return toolNames;
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                UIToolBox.TOOL_CLICKED
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        switch (notification.getName()) {
            case UIToolBox.TOOL_CLICKED:
                currentTool = notification.getBody();
                facade.sendNotification(TOOL_SELECTED, currentTool);
                break;
        }
    }

    public void setCurrentTool(String tool) {
        viewComponent.setCurrentTool(tool);
        currentTool = tool;
    }
}
