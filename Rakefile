# -*- coding: utf-8 -*-

desc "dev"
task :dev do
    Dir.chdir("project") do
        sh "boot dev"
    end
end

desc "check_data"
task :check_data do
    Dir.chdir("project") do
        sh "boot check-data"
    end
end

desc "dist"
task :dist do
    Dir.chdir("project") do
        sh "boot prod"
    end
end

desc "codeformat"
task :codeformat do
    Dir.chdir("project") do
        sh "boot codeformat"
    end
end

task :default do
    sh "rake -T"
end
