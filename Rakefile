# -*- coding: utf-8 -*-

desc "develop mode"
task :dev do
    Dir.chdir("project") do
        sh "boot dev"
    end
end

desc "validate data file"
task :check_data do
    Dir.chdir("project") do
        sh "boot check-data"
    end
end

desc "generate github-pages"
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
