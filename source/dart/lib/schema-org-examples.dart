import 'dart:async';
import 'dart:io';
import 'package:path/path.dart' as path;
import 'package:package_config/package_config.dart';

Future<String> loadExample(String fPath) async {
  final packageConfig = await findPackageConfig(Directory.current);
  if (packageConfig == null) {
    throw Exception('Package configuration not found.');
  }

  final package = packageConfig['schema-org-examples'];
  if (package == null) {
    throw Exception('schema-org-examples package not found in package configuration.');
  }
  final packageRoot = package.packageUriRoot.resolve('../data/').toFilePath();

  final dataPath = path.join(packageRoot, fPath);
  return File(dataPath).readAsString();
}
