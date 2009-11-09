/*
 * AppController.j
 * hello
 *
 * Created by You on September 27, 2009.
 * Copyright 2009, Your Company All rights reserved.
 */

@import <Foundation/CPObject.j>


@implementation AppController : CPObject
{
	CPTextField label;
    
}

- (void)applicationDidFinishLaunching:(CPNotification)aNotification
{
    var theWindow = [[CPWindow alloc] initWithContentRect:CGRectMakeZero() styleMask:CPBorderlessBridgeWindowMask],
        contentView = [theWindow contentView];

    label = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];

    [label setStringValue:@"Hello World!"];
    [label setFont:[CPFont boldSystemFontOfSize:24.0]];

    [label sizeToFit];

    [label setAutoresizingMask:CPViewMinXMargin | CPViewMaxXMargin | CPViewMinYMargin | CPViewMaxYMargin];
    [label setCenter:[contentView center]];
	[label setAlignment:CPCenterTextAlignment];

    [contentView addSubview:label];
	
	var button = [[CPButton alloc] initWithFrame: CGRectMake(
	                CGRectGetWidth([contentView bounds])/2.0 - 40,
	                CGRectGetMaxY([label frame]) + 10,
	                80, 24
	             )];

	[button setAutoresizingMask:CPViewMinXMargin | 
	                            CPViewMaxXMargin | 
	                            CPViewMinYMargin | 
	                            CPViewMaxYMargin];

	[button setTitle:"swap"];

	[button setTarget:self];
	[button setAction:@selector(swap:)];                

	[contentView addSubview:button];
    [theWindow orderFront:self];

    // Uncomment the following line to turn on the standard menu bar.
    [CPMenu setMenuBarVisible:YES];
}

- (void)swap:(id)sender
{
    if ([label stringValue] == "Hello World!")
        [label setStringValue:"Goodbye!"];
    else
        [label setStringValue:"Hello World!"];
}

@end
