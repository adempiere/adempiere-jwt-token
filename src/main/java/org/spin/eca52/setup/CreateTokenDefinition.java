/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2015 E.R.P. Consultores y Asociados, C.A.               *
 * All Rights Reserved.                                                       *
 * Contributor(s): Yamel Senih www.erpya.com                                  *
 *****************************************************************************/
package org.spin.eca52.setup;

import java.util.Properties;

import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.spin.eca52.security.JWT;
import org.spin.eca52.util.JWTUtil;
import org.spin.model.MADTokenDefinition;
import org.spin.util.ISetupDefinition;

/**
 * A JWT Definition for generate ADempiere Token
 * @author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class CreateTokenDefinition implements ISetupDefinition {

	private static final String DESCRIPTION = "A JWT definition for Third Party Access, see https://jwt.io/";
	private static final String VALUE = "JWT";
	private static final String NAME = "JWT Definition";
	
	@Override
	public String doIt(Properties context, String transactionName) {
		//	Add Token Definition
		createTokenDefinition(context, transactionName);

		//	Add System Configurator
		createSystemConfigurator(context, transactionName);

		//	token definition
		return "@AD_SetupDefinition_ID@ @Ok@";
	}
	
	/**
	 * Create Token Definition
	 * @param context
	 * @param transactionName
	 * @return
	 */
	private MADTokenDefinition createTokenDefinition(Properties context, String transactionName) {
		MADTokenDefinition tokenDefinition = new Query(context, MADTokenDefinition.Table_Name, MADTokenDefinition.COLUMNNAME_Classname + " = ?", transactionName)
				.setParameters(JWT.class.getName())
				.setClient_ID()
				.<MADTokenDefinition>first();
		//	Validate
		if(tokenDefinition != null
				&& tokenDefinition.getAD_TokenDefinition_ID() > 0) {
			return tokenDefinition;
		}
		//	
		tokenDefinition = new MADTokenDefinition(context, 0, transactionName);
		tokenDefinition.setAD_Org_ID(0);
		tokenDefinition.setTokenType(MADTokenDefinition.TOKENTYPE_ThirdPartyAccess);
		tokenDefinition.setValue(VALUE);
		tokenDefinition.setName(NAME);
		tokenDefinition.setDescription(DESCRIPTION);
		tokenDefinition.setClassname(JWT.class.getName());
		tokenDefinition.setIsHasExpireDate(false);
		tokenDefinition.setExpirationTime(Env.ZERO);
		tokenDefinition.saveEx();
		return tokenDefinition;
	}


	private MSysConfig createSystemConfigurator(Properties context, String transactionName) {
		MSysConfig secretKey = MSysConfig.get(context, JWTUtil.ECA52_JWT_SECRET_KEY, transactionName);
		if (secretKey != null) {
			return secretKey;
		}
		//
		secretKey = new MSysConfig(context, 0, transactionName);
		secretKey.setEntityType(JWTUtil.ECA52_EntityType);
		secretKey.setAD_Org_ID(0);
		secretKey.setConfigurationLevel(MSysConfig.CONFIGURATIONLEVEL_Client);
		secretKey.setValue("");
		secretKey.setName(JWTUtil.ECA52_JWT_SECRET_KEY);
		secretKey.setDescription("A Secret Key for generate JWT based token, fill it");
		secretKey.saveEx();
		return secretKey;
	}

}
