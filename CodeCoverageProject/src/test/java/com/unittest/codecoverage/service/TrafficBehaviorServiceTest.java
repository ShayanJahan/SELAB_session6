package com.unittest.codecoverage.service;

import com.unittest.codecoverage.models.StreetDirectionFlow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.unittest.codecoverage.exceptions.BehaviorException;
import com.unittest.codecoverage.models.Footpassenger;
import com.unittest.codecoverage.models.Traffic;
import com.unittest.codecoverage.models.TrafficLigth;
import com.unittest.codecoverage.services.TrafficBehaviorService;
import com.unittest.codecoverage.services.impl.TrafficBehaviorServiceImpl;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrafficBehaviorServiceTest {
	
	private TrafficBehaviorService trafficBehaviorService = new TrafficBehaviorServiceImpl();
	
	@Test
	public void testFootpassengerCrossTheStreet_shouldThrowBehaviorExceptionWhenFootpassengerCrossesTheRoadDuringHeavyTrafficAndWhileTheTrafficLightIsRed() {
	
		Traffic currentTrafic = new Traffic();
		currentTrafic.setIntenseCarTraffic(true);
		
		Footpassenger currentFootpassengerBehavior = new Footpassenger();
		currentFootpassengerBehavior.setCrossedTheRoad(true);
		currentFootpassengerBehavior.setCrossedTrafficLigth(TrafficLigth.RED);
		
		Assertions.assertThatThrownBy(() -> trafficBehaviorService.footpassengerCrossTheStreet(currentTrafic, currentFootpassengerBehavior))
			.isInstanceOf(BehaviorException.class)
			.hasMessageContaining("You should'nt do that, reckless person");
		
	}

	@Test
	public void testStreetSituation() {
		Traffic currentTraffic = new Traffic();
		currentTraffic.setStreetDirectionFlow(StreetDirectionFlow.ONE_WAY);
		Footpassenger currentFootpassengerBehavior = new Footpassenger();
		currentFootpassengerBehavior.setCrossedTheCrosswalk(true);
		currentTraffic.setMaxSpeedAllowed((short) 50);
		currentTraffic.setMinSpeedAllowed((short) 20);
		assertTrue(currentTraffic.getMaxSpeedAllowed() < (short) 60);
	}
	
	@Test
	@DisplayName("Should throw exception when footpassenger crosses the road during heavy traffic without attention")
	public void testFootpassengerCrossTheStreet_shouldThrowBehaviorExceptionWhenFootpassengerCrossesTheRoadDuringHeavyTrafficWithoutLookSides() {
	
		Traffic currentTrafic = new Traffic();
		currentTrafic.setIntenseCarTraffic(true);
		
		Footpassenger currentFootpassengerBehavior = new Footpassenger();
		currentFootpassengerBehavior.setCrossedTheRoad(true);
		currentFootpassengerBehavior.setCrossedTrafficLigth(TrafficLigth.GREEN);
		currentFootpassengerBehavior.setLookedToTheLeft(false);
		currentFootpassengerBehavior.setLookedToTheRight(false);
		currentFootpassengerBehavior.setCrossedTheCrosswalk(true);
		assertTrue(currentFootpassengerBehavior.crossedTheCrosswalk() == true);
		
		Assertions.assertThatThrownBy(() -> trafficBehaviorService.footpassengerCrossTheStreet(currentTrafic, currentFootpassengerBehavior))
			.isInstanceOf(BehaviorException.class)
			.hasMessage("You should be more careful");
		
	}

}
