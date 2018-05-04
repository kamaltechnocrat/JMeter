#!/usr/bin/env ruby
require 'zip/zipfilesystem'
require "rexml/document"
include REXML
require "ftools"


File.copy("C:\\G3_Workspace\\Oracle\\grantium-ds.xml", "C:\\DevTools\\jboss-4.0.5.GA\\server\\default\\deploy\\grantium-ds.xml")

system("cmd /c C:\\G3_Workspace\\Clean_JBOSS_Temp.bat")
 
#system("cmd /c C:\\DevTools\\jboss-4.0.5.GA\\bin\\run.bat")