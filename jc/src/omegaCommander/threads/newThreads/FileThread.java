/*
 * This file is part of jc, http://www.jcommander.narod.ru
 * Copyright (C) 2005-2010 Pavel Ovcharov
 *
 * jc is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * FileThread.java
 * Created on 14.04.2009 16:31:21
 */

package omegaCommander.threads.newThreads;

import omegaCommander.fileSystem.AbsoluteFile;
//import omegaCommander.gui.dialog.ReplaceDialog;
import omegaCommander.gui.dialog.RewriteDialog;
import omegaCommander.gui.dialog.WarningDialog;
import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */

public abstract class FileThread extends BaseThread {

	protected RewriteDialog.ReplaceResult replaceResult;

	protected enum ErrorAction {

		Skip, SkipAll, Retry,
	};
	protected ErrorAction resultError = ErrorAction.Skip;

	protected boolean applyToAll = false;
	/**
	 * ¬озвращает true если можно продолжать поток
	 */
	protected boolean queryReplace(AbsoluteFile source, AbsoluteFile target) {
		
//		if (applyToAll) {
//			return true;
//		}
//		ReplaceDialog rd = new ReplaceDialog();
//		rd.showReplaceAll(parent, source, target);
//		int dialogResult = rd.getDialogResult();
//		applyToAll = rd.getApplyToAll();
//
//		switch (dialogResult) {
//			case ReplaceDialog.RESULT_REPLACE:
//
//				resultReplace = ReplaceAction.Replace;
//				break;
//			case ReplaceDialog.RESULT_SKIP:
//				resultReplace = ReplaceAction.Skip;
//				break;
//			case ReplaceDialog.RESULT_REPLACE_OLD:
//				resultReplace = ReplaceAction.ReplaceOld;
//				break;
//			default:
//				return false;
//		}
//
//		return true;
		if (applyToAll) {
			return true;
		}
		RewriteDialog rd = new RewriteDialog(parent, source, target);
		rd.setVisible(true);
		
		applyToAll = rd.getApplyToAll();
		replaceResult = rd.getDialogResult();
		if (null == replaceResult || RewriteDialog.ReplaceResult.CANCEL == replaceResult)
			return false;

		if (RewriteDialog.ReplaceResult.REPLACE == replaceResult && rd.getApplyToOlder()) {
			replaceResult = RewriteDialog.ReplaceResult.REPLACE_OLD;
		}
		return true;

	}

	/**
	 * ¬озвращает true если можно продолжать поток
	 */
	protected boolean queryError(String errorText) {
		if (resultError.equals(ErrorAction.SkipAll)) {
			return true;
		}
		LanguageBundle lb = LanguageBundle.getInstance();
		Object[] options = new Object[]{lb.getString("StrSkip"), lb.getString("StrSkipAll"), lb.getString("StrRetry"), lb.getString("StrCancel")};
		int result = WarningDialog.showMessage(parent, errorText, lb.getString("StrJC"), options, WarningDialog.MESSAGE_ERROR, 0);
		switch (result) {
			case 0:
				resultError = ErrorAction.Skip;
				break;
			case 1:
				resultError = ErrorAction.SkipAll;
				break;
			case 2:
				resultError = ErrorAction.Retry;
				break;
			default:
				return false;
		}

		return true;
	}

}
