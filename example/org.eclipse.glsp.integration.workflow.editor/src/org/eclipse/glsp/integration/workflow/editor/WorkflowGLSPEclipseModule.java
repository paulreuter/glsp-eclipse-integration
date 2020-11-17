/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package org.eclipse.glsp.integration.workflow.editor;

import javax.websocket.Endpoint;

import org.eclipse.glsp.example.workflow.WorkflowGLSPModule;
import org.eclipse.glsp.integration.editor.actions.InvokeCopyAction;
import org.eclipse.glsp.integration.editor.actions.InvokeCutAction;
import org.eclipse.glsp.integration.editor.actions.InvokePasteAction;
import org.eclipse.glsp.integration.editor.actions.handlers.SetClipboardDataActionHandler;
import org.eclipse.glsp.integration.editor.clipboard.ClipboardService;
import org.eclipse.glsp.integration.editor.clipboard.ui.DisplayClipboardService;
import org.eclipse.glsp.integration.editor.di.EclipseEditorActionDispatcher;
import org.eclipse.glsp.integration.editor.operations.handlers.EclipsePasteOperationHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.gmodel.PasteOperationHandler;
import org.eclipse.glsp.server.utils.MultiBinding;
import org.eclipse.glsp.server.websocket.GLSPServerEndpoint;

class WorkflowGLSPEclipseModule extends WorkflowGLSPModule {
   @Override
   public void configure() {
      super.configure();
      bind(Endpoint.class).to(GLSPServerEndpoint.class);
      bind(ClipboardService.class).to(DisplayClipboardService.class);
   }

   @Override
   protected Class<? extends ActionDispatcher> bindActionDispatcher() {
      return EclipseEditorActionDispatcher.class;
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> bindings) {
      super.configureActionHandlers(bindings);
      bindings.add(SetClipboardDataActionHandler.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler> bindings) {
      super.configureOperationHandlers(bindings);
      bindings.remove(PasteOperationHandler.class);
      bindings.add(EclipsePasteOperationHandler.class);
   }

   @Override
   protected void configureClientActions(final MultiBinding<Action> bindings) {
      super.configureClientActions(bindings);
      bindings.add(InvokeCopyAction.class);
      bindings.add(InvokeCutAction.class);
      bindings.add(InvokePasteAction.class);
   }

}
