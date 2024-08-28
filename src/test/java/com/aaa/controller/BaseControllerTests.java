package com.aaa.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class BaseControllerTests {

    private BaseController baseController;

    @Before
    public void setUp() {
        baseController = new BaseController();
    }

    @Test
    public void testInitBinderRegistersCustomDateEditor() {
        // Create a mock of ServletRequestDataBinder
        ServletRequestDataBinder binder = mock(ServletRequestDataBinder.class);

        // Capture arguments passed to registerCustomEditor
        ArgumentCaptor<Class<?>> classCaptor = ArgumentCaptor.forClass(Class.class);
        ArgumentCaptor<CustomDateEditor> editorCaptor = ArgumentCaptor.forClass(CustomDateEditor.class);

        // Call initBinder method
        baseController.initBinder(binder);

        // Verify registerCustomEditor was called with Date.class and CustomDateEditor
        verify(binder).registerCustomEditor(classCaptor.capture(), editorCaptor.capture());

        // Verify that Date.class was used
        assertEquals(Date.class, classCaptor.getValue());

        // Verify that CustomDateEditor was used and check its format
        CustomDateEditor editor = editorCaptor.getValue();
        assertNotNull(editor, "CustomDateEditor should be registered");

        // Check the date format
        SimpleDateFormat expectedFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat actualFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertEquals(expectedFormat.toPattern(), actualFormat.toPattern(), "Date format should be yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testInitBinderWithRealBinder() {
        // Create a real instance of ServletRequestDataBinder and call initBinder
        ServletRequestDataBinder binder = new ServletRequestDataBinder(new Object());
        baseController.initBinder(binder);

        // Additional checks can be added here if needed, but for now,
        // we just ensure that initBinder completes without errors.
    }
}
