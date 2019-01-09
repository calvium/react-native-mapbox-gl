/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "RCTMapboxAnnotation.h"

#if __has_include("RCTEventDispatcher.h")
#import "RCTEventDispatcher.h"
#import "UIView+React.h"
#import "RCTBridge.h"
#import "RCTUtils.h"
#else
#import <React/RCTEventDispatcher.h>
#import <React/UIView+React.h>
#import <React/RCTBridge.h>
#import <React/RCTUtils.h>
#endif


@implementation RCTMapboxAnnotation {
}

-(NSString *)reuseIdentifier {
    return self.id;
}

-(void)layoutSubviews {
    [super layoutSubviews];
    [self.map restoreAnnotationPosition:self.id];
}


@end
