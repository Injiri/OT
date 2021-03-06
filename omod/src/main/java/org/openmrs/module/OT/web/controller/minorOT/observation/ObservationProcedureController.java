/**
 *  Copyright 2013 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of OT module.
 *
 *  OT module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  OT module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OT module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.OT.web.controller.minorOT.observation;

import org.openmrs.api.context.Context;
import org.openmrs.module.OT.OperationTheatreService;
import org.openmrs.module.OT.model.MinorOTProcedure;
import org.openmrs.module.OT.web.util.OTScheduleModel;
import org.openmrs.module.OT.web.util.OperationTheatreUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("ObservationController")
@RequestMapping("/module/OT/observationProcedure.form")
public class ObservationProcedureController {
	@ModelAttribute("schedule")
	public MinorOTProcedure getOrder(@RequestParam("orderId") Integer orderId) {
		OperationTheatreService ots = (OperationTheatreService) Context
				.getService(OperationTheatreService.class);
		return ots.getMinorOTProcedure(orderId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showObservationForm(Model model,
			@ModelAttribute("schedule") MinorOTProcedure schedule) {
		if (schedule!= null) {
			OTScheduleModel otm = OperationTheatreUtil.generateObservationFormModel(schedule);
			model.addAttribute("procedure", otm);
		}
		return "/module/OT/minorOT/observationForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String observationProcedure(Model model,
			@ModelAttribute("schedule") MinorOTProcedure schedule,
			@RequestParam("observations") String observations) {
		if (schedule != null) {
			OperationTheatreService ots = (OperationTheatreService) Context
					.getService(OperationTheatreService.class);
			try {
				String status = ots.observationProcedure(schedule, observations);
				model.addAttribute("status", status);
			} catch(Exception e) {
				e.printStackTrace();
				model.addAttribute("status", "Failed");
			}
		}
		return "/module/OT/minorOT/observationResponse";
	}
}
