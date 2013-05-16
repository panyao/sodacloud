//
//  SodaViewController.m
//  SodaCloudIOS
//
//  Created by Jules White on 5/16/13.
//  Copyright (c) 2013 Jules White. All rights reserved.
//

#import <DCKeyValueObjectMapping/DCKeyValueObjectMapping.h>
#import <SBJson/SBJson.h>
#import "SodaBase/NSObject+ToJson.h"
#import "SodaBase/NSString+JsonObject.h"
#import "MDWamp.h"
#import "Msg.h"

#import "SodaViewController.h"


@interface SodaViewController ()

@end

@implementation SodaViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    
    // if you want debug log set this to YES, default is NO
    [MDWamp setDebug:YES];
    
    MDWamp *wamp = [[MDWamp alloc] initWithUrl:@"ws://localhost:8081" delegate:self];
    
    // set if MDWAMP should automatically try to reconnect after a network fail default YES
    [wamp setShouldAutoreconnect:YES];
    
    // set number of times it tries to autoreconnect after a fail
    [wamp setAutoreconnectMaxRetries:2];
    
    // set seconds between each reconnection try
    [wamp setAutoreconnectDelay:5];
    
    [wamp connect];
    
    
    NSString* json = @"{\"id\":\"1\"}";
    
    //NSDictionary *jsonParsed = [json JSONValue];
    
    //DCKeyValueObjectMapping *parser = [DCKeyValueObjectMapping mapperForClass: [Msg class]];
    Msg* msg = [json toJsonObject:[Msg class]];//[parser parseDictionary:jsonParsed];
    NSLog(@"MSG ID ### %@", msg.id);
    NSLog(@"MSG Json %@",[msg toJson]);
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
